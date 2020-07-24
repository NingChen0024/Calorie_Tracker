package com.example.ning.assignmnet3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.ScrollingTabContainerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;

public class DailyDietFragment extends Fragment implements View.OnClickListener{
    View vDailyDietUnit;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vDailyDietUnit = inflater.inflate(R.layout.fragment_daily_diet_unit, container, false);
        List<String> cateList = new ArrayList<String>();
        cateList.add("fruit");
        cateList.add("vegetable");
        cateList.add("meat");
        cateList.add("drink");
        cateList.add("dessert");
        cateList.add("bread");
        cateList.add("snack");
        final Spinner cateMovie = (Spinner) vDailyDietUnit.findViewById(R.id.cateSpinner);
        final Spinner foodMovie = (Spinner) vDailyDietUnit.findViewById(R.id.foodSpinner);
        final Button submitBtn = (Button) vDailyDietUnit.findViewById(R.id.submitBtn);

        final ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(),android.R.layout.simple_spinner_item ,cateList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cateMovie.setAdapter(spinnerAdapter);
        cateMovie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {

                String cateSelected = cateMovie.getSelectedItem().toString();
                GetFoodByName getFoodByName = new GetFoodByName();
                List<String> foodList = new ArrayList<>();
                try{
                    String foodJsonArray = getFoodByName.execute(cateSelected).get();
                    JSONArray userJsonArray = new JSONArray(foodJsonArray);
                    for (int index = 0; index < userJsonArray.length(); index++) {
                        JSONObject userObject = userJsonArray.getJSONObject(index);
                        foodList.add(userObject.getString("foodId") + "-" + userObject.getString("foodName"));
                    }
                    ArrayAdapter<String> spinnerAdapter2 = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item ,foodList);
                    spinnerAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    foodMovie.setAdapter(spinnerAdapter2);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemStr = foodMovie.getSelectedItem().toString();
                String[] itemArray = itemStr.split("-");
                String foodIdStr = itemArray[0];
                int foodId = Integer.parseInt(foodIdStr);
                GetFoodById getFoodById = new GetFoodById();
                try{

                    //create food object
                    String foodJsonStr = getFoodById.execute(foodId).get();
                    JSONObject obj = new JSONObject(foodJsonStr);

                    int calorieAmount = obj.getInt("calorieAmount");
                    String category = obj.getString("category");
                    int fat = obj.getInt("fat");
                    String foodName = obj.getString("foodName");
                    Double servingAmount = obj.getDouble("servingAmount");
                    String servingUnit = obj.getString("servingUnit");
                    Integer foodIdObj = new Integer(foodId);
                    Foods food = new Foods(foodIdObj, foodName, category, calorieAmount, servingUnit, servingAmount, fat);


                    //create user object
                    String name = getActivity().getIntent().getExtras().getString("username");
                    GetUserIdAsyncTask getUserIdAsyncTask = new GetUserIdAsyncTask();
                    String cred = getUserIdAsyncTask.execute(name).get();
                    JSONObject userObject = null;
                    JSONArray userJsonArray = new JSONArray(cred);
                    for (int index = 0; index < userJsonArray.length(); index++) {
                        userObject = userJsonArray.getJSONObject(index);
                    }
                    String innerUserId = userObject.getString("userId");
                    JSONObject obj2 = new JSONObject(innerUserId);
                    Integer userId = obj2.getInt("userId");
                    String firstName = obj2.getString("userName");
                    String userSurname = obj2.getString("userSurname");
                    String email = obj2.getString("email");
                    int height = obj2.getInt("height");
                    int weight = obj2.getInt("weight");
                    String address = obj2.getString("address");
                    String postcode = obj2.getString("postcode");
                    int spM = obj2.getInt("spmile");
                    int loact = obj2.getInt("loact");
                    String gender = obj2.getString("gender");
                    String dobString =obj2.getString("dob");
                    SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ssXXX");
                    Date dob = format.parse(dobString);
                    Users user = new Users(userId, firstName, userSurname, email, dob, height, weight, gender,
                            address, postcode, loact, spM);

                    //create consumption object

                    //get unique consumption id
                    GetConsumptionCount getConsumptionCount = new GetConsumptionCount();
                    int count = Integer.parseInt(getConsumptionCount.execute().get());
                    Integer consumptionId = new Integer(count + 1);
                    Date currentDate = new Date();
                    Consumption consumption = new Consumption(consumptionId, currentDate,1, food, user);
                    PostConsumption postConsumption =new PostConsumption();
                    postConsumption.execute(consumption);


                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });


        Button searchBtn = (Button) vDailyDietUnit.findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText searchText = (EditText) vDailyDietUnit.findViewById(R.id.searchText);
                String keyword = searchText.getText().toString();
                SearchTextAsyncTask searchTextGoogle = new SearchTextAsyncTask();
                SearchImageAsyncTask searchImageGoogle = new SearchImageAsyncTask();
                searchTextGoogle.execute(keyword);
                searchImageGoogle.execute(keyword);
                SetText setText = new SetText();
                setText.execute(keyword);

            }

        });
        
        Button addBtn = (Button) vDailyDietUnit.findViewById(R.id.addBtn);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditText searchText = (EditText) vDailyDietUnit.findViewById(R.id.searchText);

                String category = cateMovie.getSelectedItem().toString();
                String foodName = searchText.getText().toString();

                if (category.equals("") || foodName.equals("")) {
                    Toast.makeText(getContext(),
                            "Please Select Category and Name of Food", Toast.LENGTH_SHORT).show();
                }else{

                    try{

                        GetFoodJson getFoodJson = new GetFoodJson();
                        String jsonStr = getFoodJson.execute(foodName).get();
                        JSONObject nutrientObj = null;
                        JSONObject jsonObject = new JSONObject(jsonStr);
                        JSONArray jsonArray = jsonObject.getJSONArray("parsed");
                        if(jsonArray != null && jsonArray.length() > 0) {
                            nutrientObj =jsonArray.getJSONObject(0).getJSONObject("food").getJSONObject("nutrients");
                        }

                        GetFoodCount getFoodCount = new GetFoodCount();
                        String countStr = getFoodCount.execute().get();

                        Integer foodId = Integer.parseInt(countStr) + 1;
                        int kcal = (int) Math.round(Double.parseDouble(nutrientObj.getString("ENERC_KCAL")));
                        int fat = (int) Math.ceil(Double.parseDouble(nutrientObj.getString("FAT")));
                        Double serving = 1.0;
                        String unit = "each";

                        Foods newFood = new Foods(foodId,foodName,category, kcal,unit,serving,fat);

                        PostFood postFood = new PostFood();
                        postFood.execute(newFood);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

        });

        return vDailyDietUnit;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }

    }


    private class GetFoodByName extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String...params){
            return RestClient.findFoodByCategory(params[0]);
        }
        @Override
        protected void onPostExecute (String recordPassword){
        }
    }

    private class GetFoodById extends AsyncTask<Integer, Void, String> {
        @Override
        protected String doInBackground (Integer...params){
            return RestClient.getFoodById(params[0]);
        }
        @Override
        protected void onPostExecute (String recordPassword){
        }
    }

    private class PostConsumption extends AsyncTask<Consumption, Void, Boolean> {
        @Override
        protected Boolean doInBackground (Consumption...params){
             RestClient.createConsumption(params[0]);
             return true;
        }
        @Override
        protected void onPostExecute (Boolean result){
            if(result){
                Toast.makeText(getContext(),
                        "new consumption added", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class GetUserIdAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.findCredByUsername(params[0]);
        }

        @Override
        protected void onPostExecute(String recordPassword) {
        }
    }

    private class GetConsumptionCount extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return RestClient.countConsumption();
        }
        @Override
        protected void onPostExecute(String recordPassword) {
        }
    }

    private class GetFoodCount extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return RestClient.countFood();
        }
        @Override
        protected void onPostExecute(String recordPassword) {
        }
    }

    public String getSnippet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                snippet =jsonArray.getJSONObject(1).getString("snippet");
            }
        }catch (Exception e){ e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }

    public String getImage(String result){
        String image = null;
        String pagemap = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                pagemap =jsonArray.getJSONObject(1).getString("pagemap");
            }
            JSONObject pagemapObject = new JSONObject(pagemap);
            JSONArray imageArray = pagemapObject.getJSONArray("cse_image");
            image =imageArray.getJSONObject(0).getString("src");

        }catch (Exception e){ e.printStackTrace();
            image = "NO INFO FOUND";
        }
        return image;
    }


    private class SearchTextAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String result = RestClient.googleSearch(params[0], new String[]{"num"}, new String[]{"5"});
            String text = getSnippet(result);
            return text;
        }
        @Override
        protected void onPostExecute(String text) {
            TextView tv = (TextView) vDailyDietUnit.findViewById(R.id.googleText);
            tv.setText(text);
        }
    }

    private class SearchImageAsyncTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            String result = RestClient.googleSearch(params[0], new String[]{"num"}, new String[]{"5"});
            String imageURL = getImage(result);
            try {
                URL url = new URL(imageURL);
                Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                return image;
            } catch (IOException e) {
                System.out.println(e);
            }
            return null;
        }
        @Override
        protected void onPostExecute(Bitmap result) {
            if(result != null) {
                ImageView iv = (ImageView) vDailyDietUnit.findViewById(R.id.googleImage);
                iv.setImageBitmap(result);
            }
        }
    }

    private class GetFoodJson extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.foodSearch(params[0]);
        }

        @Override
        protected void onPostExecute(String jsonStr) {

        }
    }

    private class SetText extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return RestClient.foodSearch(params[0]);
        }

        @Override
        protected void onPostExecute(String jsonStr) {
            try{

                JSONObject nutrientObj = null;
                JSONObject jsonObject = new JSONObject(jsonStr);
                JSONArray jsonArray = jsonObject.getJSONArray("parsed");
                if(jsonArray != null && jsonArray.length() > 0) {
                    nutrientObj =jsonArray.getJSONObject(0).getJSONObject("food").getJSONObject("nutrients");
                }

                String nutrition = nutrientObj.getString("ENERC_KCAL");
                String fat = nutrientObj.getString("FAT");

                TextView cal = (TextView) vDailyDietUnit.findViewById(R.id.calText);
                cal.setText("ENERC_KCAL: " + nutrition + "        " + "FAT: " + fat);


            }  catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }

    private class PostFood extends AsyncTask<Foods, Void, Boolean> {
        @Override
        protected Boolean doInBackground (Foods...params){
            RestClient.createFood(params[0]);
            return true;
        }
        @Override
        protected void onPostExecute (Boolean result){
            if(result){
                Toast.makeText(getContext(),
                        "New Food Added", Toast.LENGTH_SHORT).show();
            }
        }
    }


}
