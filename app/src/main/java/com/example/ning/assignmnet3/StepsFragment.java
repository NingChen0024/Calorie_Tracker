package com.example.ning.assignmnet3;

import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class StepsFragment extends Fragment implements View.OnClickListener{
    View vStepsUnit;
    ReportDatabase db = null;
    String pastRecord = "";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vStepsUnit = inflater.inflate(R.layout.fragment_steps_unit, container, false);
        TextView Text = (TextView) vStepsUnit.findViewById(R.id.historyView);
        Text.setMovementMethod(new ScrollingMovementMethod());

        Button btn = (Button) vStepsUnit.findViewById(R.id.stepsBtn);
        btn.setOnClickListener(this);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                ReportDatabase.class, "ReportDatabase").fallbackToDestructiveMigration()
                .build();
        return vStepsUnit;

    }

    @Override
    public void onClick(View v){
        EditText editText = (EditText) vStepsUnit.findViewById(R.id.stepsInput);
        String newText = editText.getText().toString();
        TextView history = (TextView) vStepsUnit.findViewById(R.id.historyView);
        Boolean isvalid = validation(newText);
        Integer stepsInDb;
        if(isvalid){
            Date currentDate = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            String strDate = dateFormat.format(currentDate);
            ReadDatabase database = new ReadDatabase();

            try{
                stepsInDb = database.execute().get();

                int addedSteps = Integer.parseInt(newText);
                Integer totalSteps = new Integer( addedSteps + stepsInDb);
                UpdateDatabase upDatabase = new UpdateDatabase();
                upDatabase.execute(totalSteps);

                pastRecord = pastRecord + "Date: " + strDate + System.getProperty("line.separator") + "Steps: " + totalSteps + System.getProperty("line.separator");
                history.setText(pastRecord);
                editText.setText("");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        }
    }

    private class ReadDatabase extends AsyncTask<Void, Void, Integer> {

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

    private class UpdateDatabase extends AsyncTask<Integer, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Integer... params) {
            Report report = db.ReportDao().getAll().get(0);
            int result = new Integer(params[0]);
            report.setTotalSteps(result);
            db.ReportDao().updateReport(report);
            return true;
        }

        @Override
        protected void onPostExecute(Boolean response) {
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
            Toast.makeText(getActivity().getApplicationContext(),
                    "Please enter a integer", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(result < 0){
            Toast.makeText(getActivity().getApplicationContext(),
                    "Please enter a positive number", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}