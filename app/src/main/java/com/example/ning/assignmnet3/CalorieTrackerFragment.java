package com.example.ning.assignmnet3;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CalorieTrackerFragment extends Fragment {
    View vCalorieUnit;
    ReportDatabase db = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vCalorieUnit = inflater.inflate(R.layout.fragment_calorie_tracker_unit, container, false);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                ReportDatabase.class, "ReportDatabase").fallbackToDestructiveMigration()
                .build();

        TextView calorieGoalText = (TextView) vCalorieUnit.findViewById(R.id.calorieGoal);
        TextView stepsTakenText = (TextView) vCalorieUnit.findViewById(R.id.stepsTaken);
        TextView totalConsumedText = (TextView) vCalorieUnit.findViewById(R.id.totalConsumed);
        TextView totalBurnedText = (TextView) vCalorieUnit.findViewById(R.id.totalBurned);

        String cred = "";
        String userId = "";

        GetCalorieGoal getCalorieGoal = new GetCalorieGoal();
        GetStepsTaken getStepsTaken = new GetStepsTaken();
        try{

            String goalStr = Integer.toString(getCalorieGoal.execute().get());
            String stepStr = Integer.toString(getStepsTaken.execute().get());
            calorieGoalText.setText(goalStr);
            stepsTakenText.setText(stepStr);
            //get user id by username in the cred
            String name = getActivity().getIntent().getExtras().getString("username");
            GetUserIdAsyncTask getUserIdAsyncTask = new GetUserIdAsyncTask();
            cred = getUserIdAsyncTask.execute(name).get();
            JSONArray userJsonArray = new JSONArray(cred);
            for (int index = 0; index < userJsonArray.length(); index++) {
                JSONObject userObject = userJsonArray.getJSONObject(index);
                userId = userObject.getString("userId");
            }



            //get totalBurned
            JSONObject obj = new JSONObject(userId);
            int realIdInt = Integer.parseInt(obj.getString("userId"));
            Integer realId = new Integer(realIdInt);
            Integer realSteps = Integer.parseInt(stepStr);
            GetCalorieBurned getCalorieBurned = new GetCalorieBurned();
            Double rowCalorieBurned = getCalorieBurned.execute(realId, realSteps).get();
            Double calorieBurned = Math.round(rowCalorieBurned * 10) / 10.0;



            //get totalConsumed
            GetCalorieConsumed getCalorieConsumed = new GetCalorieConsumed();
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String dateStr = dateFormat.format(date);
            String realIdStr = Integer.toString(realId);
            Integer calorieConsumed = getCalorieConsumed.execute(realIdStr, dateStr).get();

            String burnedStr = Double.toString(calorieBurned);
            String consumedStr = Integer.toString(calorieConsumed);

            totalConsumedText.setText(consumedStr);
            totalBurnedText.setText(burnedStr);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }  catch (JSONException e) {
            e.printStackTrace();
        }



        Button createBtn = (Button) vCalorieUnit.findViewById(R.id.reportBtn);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 try{

                     //get report id
                     CountReport countReport = new CountReport();
                     Integer reportId = Integer.parseInt(countReport.execute().get()) + 1;

                     // get userId
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

                     //get date
                     Date date = new Date();

                         int calorieGoal = Integer.parseInt(calorieGoalText.getText().toString());
                         int stepsTaked = Integer.parseInt(stepsTakenText.getText().toString());
                         int totalConsumed = Integer.parseInt(totalConsumedText.getText().toString());
                         int totalBurned = (int) Double.parseDouble(totalBurnedText.getText().toString());

                         NewReport report = new NewReport(reportId, date, totalConsumed, totalBurned, stepsTaked, calorieGoal, user);
                         PostReport postReport = new PostReport();
                         postReport.execute(report);
                         ClearDatabase clearDatabase = new ClearDatabase();
                         clearDatabase.execute();

                         calorieGoalText.setText("");
                         stepsTakenText.setText("");
                         totalConsumedText.setText("");
                         totalBurnedText.setText("");

                 } catch (InterruptedException e) {
                    e.printStackTrace();
                 } catch (ExecutionException e) {
                    e.printStackTrace();
                 }catch (JSONException e) {
                     e.printStackTrace();
                 }catch (ParseException e) {
                     e.printStackTrace();
                 }
            }

        });

        return vCalorieUnit;

    }

    private class GetCalorieBurned extends AsyncTask<Integer, Void, Double> {
        @Override
        protected Double doInBackground(Integer... params) {
            String burnPerStepStr =  RestClient.findCalorieBurnedByStepByUserId(params[0]);
            Double burnPerStep = Double.parseDouble(burnPerStepStr);
            Double burnByStep = burnPerStep * params[1];

            String burnAtRestStr = RestClient.findCalorieBurnedAtRestByUserId(params[0]);
            Double burnAtRest = Double.parseDouble(burnAtRestStr);

            Double totalBurned = burnByStep + burnAtRest;
            return totalBurned;
        }
        @Override
        protected void onPostExecute(Double recordPassword) {
        }
    }

    private class GetCalorieConsumed extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... params) {
            int userId = Integer.parseInt(params[0]);
            String totalConsumed =  RestClient.findCalorieConsumedByUserIdAndDate(userId, params[1]);
            Double consumedDob = Double.parseDouble(totalConsumed);
            int consumedInt = consumedDob.intValue();
            return consumedInt;
        }
        @Override
        protected void onPostExecute(Integer recordPassword) {
        }
    }

    private class GetCalorieGoal extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            List<Report> reports = db.ReportDao().getAll();
            Report report = reports.get(0);
            int goal = report.getCalorieGoal();
            return goal;
        }

        @Override
        protected void onPostExecute(Integer result) {
        }
    }

    private class GetStepsTaken extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... params) {
            List<Report> reports = db.ReportDao().getAll();
            Report report = reports.get(0);
            int steps = report.getTotalSteps();
            return steps;
        }

        @Override
        protected void onPostExecute(Integer result) {
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

    private class CountReport extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return RestClient.countReport();
        }

        @Override
        protected void onPostExecute(String recordPassword) {
        }
    }

    private class PostReport extends AsyncTask<NewReport, Void, Boolean> {
        @Override
        protected Boolean doInBackground (NewReport...params){
            RestClient.createReport(params[0]);
            return true;
        }
        @Override
        protected void onPostExecute (Boolean result){
            if(result){
                Toast.makeText(getContext(),
                        "New Report Added", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class ClearDatabase extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            Report reportSQL = db.ReportDao().getAll().get(0);
            reportSQL.setTotalSteps(0);
            reportSQL.setTotalSteps(0);
            reportSQL.setTotalBurned(0);
            reportSQL.setCalorieGoal(0);
            db.ReportDao().updateReport(reportSQL);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean response) {
            if (response) {
                Toast.makeText(getContext(),
                        "Database cleared", Toast.LENGTH_SHORT).show();
            }
        }
    }

}