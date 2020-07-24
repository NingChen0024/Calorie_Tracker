package com.example.ning.assignmnet3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class SignUpPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_page);

        final EditText firstNameEditText = (EditText) findViewById(R.id.firstName);
        final EditText surnameEditText = (EditText) findViewById(R.id.surname);
        final EditText emailEditText = (EditText) findViewById(R.id.email);
        final EditText heightEditText = (EditText) findViewById(R.id.height);
        final EditText weightEditText = (EditText) findViewById(R.id.weight);
        final EditText addressEditText = (EditText) findViewById(R.id.address);
        final EditText postcodeEditText = (EditText) findViewById(R.id.postcode);
        final EditText SpMEditText = (EditText) findViewById(R.id.stepPerMile);
        final EditText userNameEditText = (EditText) findViewById(R.id.username);
        final EditText passwordEditText = (EditText) findViewById(R.id.password);
        final EditText dobEditText = (EditText) findViewById(R.id.DoB);

        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "MM/dd/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                dobEditText.setText(sdf.format(myCalendar.getTime()));
            }
        };
        dobEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(SignUpPage.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        //activity Spinner
        final Spinner staticSpinner = (Spinner) findViewById(R.id.actSpinner);
        // Create an ArrayAdapter using the string array and a default spinner
        ArrayAdapter<CharSequence> staticAdapter = ArrayAdapter
                .createFromResource(this, R.array.brew_array,
                                    android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        staticAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        staticSpinner.setAdapter(staticAdapter);



        //gender radio button
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.genderRadio);
        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get selected gender radio button from radioGroup
                    int selectedId = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) findViewById(selectedId);
                    String newGender = radioButton.getText().toString();
                    String gender = "";
                    
                    if(newGender.equals("Male")){
                         gender = "M";
                    }else if(newGender.equals("Female")){
                         gender = "F";
                    }else{
                         gender = "O";
                    }

                    ArrayList<String> usernameList = new ArrayList<>();
                    ArrayList<String> credList = new ArrayList<>();

                    String userString = "";
                    String credString = "";

                    usernameAsyncTask userAsy = new usernameAsyncTask();
                    credentialAsyncTask credAsy = new credentialAsyncTask();

                    try{
                        userString = userAsy.execute().get();
                        credString = credAsy.execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                    //get all the emails and username and put it into arrayList
                    try {
                        JSONArray userJsonArray = new JSONArray(userString);
                        for (int index = 0; index < userJsonArray.length(); index++) {
                            JSONObject userObject = userJsonArray.getJSONObject(index);
                            usernameList.add(userObject.getString("email"));
                        }

                        JSONArray credentialJsonArray = new JSONArray(credString);
                        for (int index = 0; index < credentialJsonArray.length(); index++) {
                            JSONObject credObject = credentialJsonArray.getJSONObject(index);
                            credList.add(credObject.getString("userName"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    //validation
                    String firstname = firstNameEditText.getText().toString();
                    String surname = surnameEditText.getText().toString();
                    String email = emailEditText.getText().toString();
                    String heightString = heightEditText.getText().toString();
                    String weightString = weightEditText.getText().toString();
                    String address = addressEditText.getText().toString();
                    String postcode = postcodeEditText.getText().toString();
                    String spMString = SpMEditText.getText().toString();
                    String username = userNameEditText.getText().toString();
                    String password = passwordEditText.getText().toString();
                    String dobString =dobEditText.getText().toString();
                    Date dob = null;
                    boolean invalid = validation(firstname, surname, email, heightString, weightString, address,
                            postcode, spMString, username, password, dobString, usernameList, credList);

                   if(!invalid)
                    {
                        MD5 hash = new MD5();
                        String hashedPassword = hash.getMd5(password);
                        int height=Integer.parseInt(heightString);
                        int weight=Integer.parseInt(weightString);
                        int spM=Integer.parseInt(spMString);

                        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yy");
                        try {
                            dob = format.parse(dobString);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        ArrayList<String> idList = new ArrayList<>();
                        try {
                            JSONArray userJsonArray = new JSONArray(userString);
                            for (int index = 0; index < userJsonArray.length(); index++) {
                                JSONObject userObject = userJsonArray.getJSONObject(index);
                                idList.add(userObject.getString("userId"));
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //get unique user id
                        int newId = 1;
                        boolean valid = false;
                        do{
                            newId = getRandomNumber();
                            valid = true;
                            for(int index = 0; index < idList.size(); index++){
                                if (Integer.parseInt(idList.get(index)) == newId){
                                    valid = false;
                                }
                            }
                        }while(!valid);

                        String dobstring = Integer.toString(newId);
                        firstNameEditText.setText(dobstring);

                        Integer userId = new Integer(newId);


                        //get loa
                        String LoActivity = staticSpinner.getSelectedItem().toString();
                        int loa = 0;
                        if (LoActivity.equals("Level 1")){
                            loa = 1;
                        }else if(LoActivity.equals("Level 2")){
                            loa = 2;
                        }else if(LoActivity.equals("Level 3")){
                            loa = 3;
                        }else if(LoActivity.equals("Level 4")){
                            loa = 4;
                        }else{
                            loa = 5;
                        }

                        //get currentDate
                        Date currentDate = new Date();

                        //post records
                        Users user = new Users(userId, firstname, surname, email, dob,
                                               height, weight, gender, address, postcode,
                                               loa, spM );
                        Credentials cred = new Credentials(username, hashedPassword, currentDate, user);

                        PostUserAsyncTask postUser = new PostUserAsyncTask();
                        PostCredentialAsyncTask postCred = new PostCredentialAsyncTask();

                        postUser.execute(user);

                        Intent intent = new Intent(SignUpPage.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            });

    }

    public boolean validation(String firstname, String surname, String email, String height,
                              String weight, String address, String postcode, String spM,
                              String username, String password, String dob,
                              ArrayList<String> usernameList, ArrayList<String> credList){
        boolean invalid = false;
        if (firstname.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(), "Enter your FirstName",
                    Toast.LENGTH_SHORT).show();
        } else if (surname.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Please enter your surname", Toast.LENGTH_SHORT)
                    .show();
        } else if (email.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Please enter your Email", Toast.LENGTH_SHORT)
                    .show();
        } else if (height.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Please enter your Height", Toast.LENGTH_SHORT)
                    .show();

        } else if (weight.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Please enter your Weight", Toast.LENGTH_SHORT)
                    .show();
        } else if (address.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Please enter your Address", Toast.LENGTH_SHORT).show();
        } else if (postcode.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Please enter your Postcode", Toast.LENGTH_SHORT).show();
        } else if (spM.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Please enter your Steps per Mile", Toast.LENGTH_SHORT).show();
        } else if (username.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Please enter your Username", Toast.LENGTH_SHORT).show();
        } else if (password.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Please enter your Password", Toast.LENGTH_SHORT).show();
        }else if (dob.equals("")) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Please enter your Date of Birth", Toast.LENGTH_SHORT).show();
        }else{
            for (int index = 0; index < usernameList.size(); index++){
                if(email.equals(usernameList.get(index))){
                    invalid = true;
                    Toast.makeText(getApplicationContext(),
                            "This email address has been taken", Toast.LENGTH_SHORT).show();
                }
            }
            for(int index = 0; index < credList.size(); index++){
                if(username.equals(credList.get(index))){
                    invalid = true;
                    Toast.makeText(getApplicationContext(),
                            "This username has been taken", Toast.LENGTH_SHORT).show();
                }
            }
        }

        try {
            Integer.parseInt(firstname);
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Firstname should not be number", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException nfe) {

        }

        try {
            Integer.parseInt(surname);
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Surname should not be number", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException nfe) {

        }

        try {
            Integer.parseInt(email);
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Email should not be number", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException nfe) {

        }

        try {
            Integer.parseInt(height);
        } catch (NumberFormatException nfe) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Height should be number", Toast.LENGTH_SHORT).show();
        }

        try {
            Integer.parseInt(weight);
        } catch (NumberFormatException nfe) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Weight should be number", Toast.LENGTH_SHORT).show();
        }

        try {
            Integer.parseInt(address);
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Address should be number", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException nfe) {

        }

        try {
            Integer.parseInt(postcode);
        } catch (NumberFormatException nfe) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Postcode should be number", Toast.LENGTH_SHORT).show();
        }

        try {
            Integer.parseInt(spM);
        } catch (NumberFormatException nfe) {
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Steps per mile should be number", Toast.LENGTH_SHORT).show();
        }

        try {
            Integer.parseInt(username);
            invalid = true;
            Toast.makeText(getApplicationContext(),
                    "Username should not be number", Toast.LENGTH_SHORT).show();
        } catch (NumberFormatException nfe) {

        }




        return invalid;

    }

    public int getRandomNumber (){
        Random r = new Random();
        int low = 10;
        int high = 100;
        int result = r.nextInt(high-low) + low;
        return result;
    }

    private class usernameAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String...params){
            return RestClient.findAllUsers(); }
        @Override
        protected void onPostExecute (String recordPassword){
        }
    }

    private class credentialAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String...params){
            return RestClient.findAllCredentials(); }
        @Override
        protected void onPostExecute (String recordPassword){
        }
    }

    private class PostUserAsyncTask extends AsyncTask<Users, Void, Users> {
        @Override
        protected Users doInBackground(Users... params) {
            Users user = params[0];
            RestClient.createUser(user);
            return user;
        }
        @Override
        protected void onPostExecute(Users user) {

                EditText userNameEditText = (EditText) findViewById(R.id.username);
                EditText passwordEditText = (EditText) findViewById(R.id.password);
                String username = userNameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                MD5 hash = new MD5();
                String hashedPassword = hash.getMd5(password);
                Date currentDate = new Date();

                Credentials cred = new Credentials(username, hashedPassword, currentDate, user);
                PostCredentialAsyncTask postCred = new PostCredentialAsyncTask();

                Toast.makeText(getApplicationContext(),
                        "new user created", Toast.LENGTH_SHORT).show();

                postCred.execute(cred);

        }
    }

    private class PostCredentialAsyncTask extends AsyncTask<Credentials, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Credentials... params) {
            Credentials cred = params[0];
            RestClient.createCredential(cred);
            return true;
        }
        @Override
        protected void onPostExecute(Boolean response) {
            if (response == true){
                Toast.makeText(getApplicationContext(),
                        "new credential created", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
