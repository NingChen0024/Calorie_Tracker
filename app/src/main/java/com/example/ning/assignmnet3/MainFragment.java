package com.example.ning.assignmnet3;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {
    View vMain;
    ReportDatabase db = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        vMain = inflater.inflate(R.layout.fragment_main, container, false);

        TextView welcomeText = (TextView) vMain.findViewById(R.id.welcomeView);
        TextClock textClock = vMain.findViewById(R.id.timer);
        String name = getActivity().getIntent().getExtras().getString("username");

        SharedPreferences.Editor editor = getContext().getSharedPreferences("userName", MODE_PRIVATE).edit();
        editor.putString("name", name);
        editor.apply();
        startAlarm();

        String password = getActivity().getIntent().getExtras().getString("password");
        textClock.setFormat12Hour("yyyy-MM-dd hh:mm:ss");
        textClock.setFormat24Hour(null);


        String cred = "";
        String userId = "";

        //user attributes
        Integer id = 0;
        String firstName = "";
        String userSurname = "";
        String email = "";
        Date dob = null;
        int height = 0;
        int weight = 0;
        String gender = "";
        String address = "";
        String postcode = "";
        int loact = 0;
        int spmile = 0;


        try {
            GetUserIdAsyncTask getUserIdAsyncTask = new GetUserIdAsyncTask();
            cred = getUserIdAsyncTask.execute(name).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        try {
            JSONArray userJsonArray = new JSONArray(cred);
            for (int index = 0; index < userJsonArray.length(); index++) {
                JSONObject userObject = userJsonArray.getJSONObject(index);
                userId = userObject.getString("userId");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            JSONObject obj = new JSONObject(userId);
            firstName = obj.getString("userName");
            id = Integer.parseInt(obj.getString("userId"));
            userSurname = obj.getString("userSurname");
            email = obj.getString("email");
            SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd'T'hh:mm:ssXXX");
            try {
                dob = format.parse(obj.getString("dob"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            height = Integer.parseInt(obj.getString("height"));
            weight = Integer.parseInt(obj.getString("weight"));
            gender = obj.getString("gender");
            address = obj.getString("address");
            postcode = obj.getString("postcode");
            loact = Integer.parseInt(obj.getString("loact"));
            spmile = Integer.parseInt(obj.getString("spmile"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        welcomeText.setText("Welcome, " + firstName);
        final Users user = new Users(id, firstName, userSurname, email, dob, height,
                weight, gender, address, postcode, loact, spmile);


        EditText calGoal = (EditText) vMain.findViewById(R.id.calorieGoal);
        Button setBtn = (Button) vMain.findViewById(R.id.goalBtn);

        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                ReportDatabase.class, "ReportDatabase").fallbackToDestructiveMigration()
                .build();

        setBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadDatabase readDatabase = new ReadDatabase();
                Boolean readResult = null;
                EditText calGoal = (EditText) vMain.findViewById(R.id.calorieGoal);
                String goalString = calGoal.getText().toString();

                boolean isvalid = validation(goalString);
                if (isvalid == true){
                    try {
                        readResult = readDatabase.execute().get();
                    } catch (InterruptedException e) {
                        e.printStackTrace(); //handle it the way you like
                    } catch (ExecutionException e) {
                        e.printStackTrace();//handle it the way you like
                    }

                    if (readResult) {
                        UpdateDatabase updateDatabase = new UpdateDatabase();
                        updateDatabase.execute(goalString);
                        Toast.makeText(getActivity().getApplicationContext(),
                                "CalorieGoal updated", Toast.LENGTH_SHORT).show();
                    } else {
                        InsertDatabase insertDatabase = new InsertDatabase();
                        insertDatabase.execute(goalString);
                        Toast.makeText(getActivity().getApplicationContext(),
                                "CalorieGoal inserted", Toast.LENGTH_SHORT).show();
                    }
                }

                ReadReport readReport = new ReadReport();
                readReport.execute();

            }

        });
        return vMain;
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

    private class ReadDatabase extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            List<Report> users = db.ReportDao().getAll();
            if (!(users.isEmpty() || users == null)) {
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }

    private class UpdateDatabase extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            Report report = db.ReportDao().getAll().get(0);
            int result = Integer.parseInt(params[0]);
            report.setCalorieGoal(result);
            db.ReportDao().updateReport(report);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean response) {
        }
    }

    private class InsertDatabase extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {

            int totalConsumed = 0;
            int totalSteps = 0;
            int totalBurned = 0;
            int result = Integer.parseInt(params[0]);
            Report report = new Report(totalConsumed, totalSteps, totalBurned, result);
            db.ReportDao().insert(report);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }

    private class ReadReport extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            List<Report> report = db.ReportDao().getAll();
            String userstr = "";
            if (!(report.isEmpty() || report == null)) {
                for (Report temp : report) {
                    userstr = (temp.getCalorieGoal() + "");
                }
            }
            return userstr;
        }
        @Override
        protected void onPostExecute (String userstr){
            EditText calGoal = (EditText) vMain.findViewById(R.id.calorieGoal);
            calGoal.setText(userstr);
        }
    }

    public boolean validation(String str){
        int result;
        if (str.equals("")) {
            Toast.makeText(getActivity().getApplicationContext(),
                    "Please enter a integer", Toast.LENGTH_SHORT).show();
            return false;
        }

        try {
            Integer.parseInt(str);
            result = Integer.parseInt(str);
        } catch (NumberFormatException nfe) {
            EditText calGoal = (EditText) vMain.findViewById(R.id.calorieGoal);
            calGoal.setText("");
            Toast.makeText(getActivity().getApplicationContext(),
                    "Please enter a integer", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(result < 0){
            EditText calGoal = (EditText) vMain.findViewById(R.id.calorieGoal);
            calGoal.setText("");
            Toast.makeText(getActivity().getApplicationContext(),
                    "Please enter a positive number", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private void startAlarm(){

        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY,23);
        c.set(Calendar.MINUTE,59);
        c.set(Calendar.SECOND,59);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(),1, intent,0);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(), pendingIntent);
    }
}