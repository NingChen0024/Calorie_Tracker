package com.example.ning.assignmnet3;


import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;


public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ReportDatabase db = Room.databaseBuilder(context.getApplicationContext(),
                ReportDatabase.class, "ReportDatabase").fallbackToDestructiveMigration()
                .build();
        SharedPreferences prefs = context.getSharedPreferences("userName", MODE_PRIVATE);
        String userName = prefs.getString("name", null);

        try{

            //get report id
            CountReport countReport = new CountReport();
            Integer reportId = Integer.parseInt(countReport.execute().get()) + 1;

            // get userId
            String name = userName;
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

            Report reportSQL = db.ReportDao().getAll().get(0);
            int stepsTaked = reportSQL.getTotalSteps();
            int totalConsumed = reportSQL.getTotalConsumed();
            int totalBurned = reportSQL.getTotalBurned();
            int calorieGoal = reportSQL.getCalorieGoal();



            NewReport report = new NewReport(reportId, date, totalConsumed, totalBurned, stepsTaked, calorieGoal, user);
            PostReport postReport = new PostReport();
            postReport.execute(report);
            reportSQL.setTotalSteps(0);
            reportSQL.setTotalSteps(0);
            reportSQL.setTotalBurned(0);
            reportSQL.setCalorieGoal(0);
            db.ReportDao().updateReport(reportSQL);



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

        }
    }

}
