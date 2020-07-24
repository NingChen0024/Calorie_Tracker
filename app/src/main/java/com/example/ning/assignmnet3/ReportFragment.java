package com.example.ning.assignmnet3;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;


public class ReportFragment extends Fragment implements View.OnClickListener {
    View vReportUnit;
    float rainfall[] = {1,2,3};
    String month[] = {"Jan", "Feb", "Mar"};
    String[] idList = new String[3];
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        vReportUnit = inflater.inflate(R.layout.fragment_report_unit, container, false);

        final TextView pieDate = (TextView) vReportUnit.findViewById(R.id.date);
        final TextView barStartDate = (TextView) vReportUnit.findViewById(R.id.dateStart);
        final TextView barEndDate = (TextView) vReportUnit.findViewById(R.id.dateEnd);

        //set date picker
        final Calendar myCalendar = Calendar.getInstance();
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                pieDate.setText(sdf.format(myCalendar.getTime()));
                barStartDate.setText(sdf.format(myCalendar.getTime()));
                barEndDate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        pieDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                barStartDate.setText(sdf.format(myCalendar.getTime()));
            }
        };

        barStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date2, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy-MM-dd"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                barEndDate.setText(sdf.format(myCalendar.getTime()));
            }
        };
        barEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getContext(), date3, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        //get user id
        String name = getActivity().getIntent().getExtras().getString("username");
        String userId = "";
        String cred = "";
        try {
            GetUserIdAsyncTask getUserIdAsyncTask = new GetUserIdAsyncTask();
            cred = getUserIdAsyncTask.execute(name).get();
            JSONArray userJsonArray = new JSONArray(cred);
            for (int index = 0; index < userJsonArray.length(); index++) {
                JSONObject userObject = userJsonArray.getJSONObject(index);
                userId = userObject.getString("userId");
            }
            JSONObject obj = new JSONObject(userId);
            idList[0] = obj.getString("userId");

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button pieBtn = (Button) vReportUnit.findViewById(R.id.checkPie);
        pieBtn.setOnClickListener(this);

        Button barBtn = (Button) vReportUnit.findViewById(R.id.checkBar);
        barBtn.setOnClickListener(this);

        String restPieDate = pieDate.getText().toString();
        String restBarStartDate = barStartDate.getText().toString();
        String restBarEndDate = barStartDate.getText().toString();
        
        return vReportUnit;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {

            case R.id.checkPie:
                GetPieData getPieData = new GetPieData();
                try {
                    TextView pieDate = (TextView) vReportUnit.findViewById(R.id.date);
                    String restPieDate = pieDate.getText().toString();

                    String mixedPieData = getPieData.execute(idList[0], restPieDate).get();
                    if(mixedPieData.equals("")){
                        Toast.makeText(getContext(),
                                "No record at this date", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String[] mixedStr = mixedPieData.split(",");
                        int totalConsumed = Integer.parseInt(mixedStr[0]);
                        int totalBurned = Integer.parseInt(mixedStr[1]);
                        int totalRemained = Integer.parseInt(mixedStr[2]);
                        setPieChart(totalConsumed, totalBurned, totalRemained);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;

            case R.id.checkBar:
                GetBarData getBarData = new GetBarData();
                try {
                    TextView barStartDate = (TextView) vReportUnit.findViewById(R.id.dateStart);
                    TextView barEndDate = (TextView) vReportUnit.findViewById(R.id.dateEnd);
                    String restBarStartDate = barStartDate.getText().toString();
                    String restBarEndDate = barEndDate.getText().toString();

                    String mixedBarData = getBarData.execute(idList[0], restBarStartDate, restBarEndDate).get();
                    if(mixedBarData.equals("0,0,0")){
                        Toast.makeText(getContext(),
                                "No record at this period", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String[] mixedStr = mixedBarData.split(",");
                        int totalConsumed = Integer.parseInt(mixedStr[0]);
                        int totalBurned = Integer.parseInt(mixedStr[1]);
                        setBarChart(totalConsumed, totalBurned);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
        }
    }


    public void setPieChart(int consumed, int burned, int remained) {

        int number[] = {consumed, burned, remained};
        String name[] = {"TotalConsumed", "TotalBurned", "TotalRemained"};

        List<PieEntry> pieEntries = new ArrayList<>();
        for (int i = 0; i < number.length; i++){
            pieEntries.add(new PieEntry(number[i], name[i]));
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "Calorie Track");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(dataSet);

        PieChart chart = (PieChart) vReportUnit.findViewById(R.id.pieChart);
        chart.animateXY(3000,3000);
        chart.setData(data);
        chart.invalidate();
        //PieChart Ends Here
    }

    public void setBarChart(int consumed, int burned){

        int number[] = {consumed, burned};

        List<BarEntry> barEntries = new ArrayList<>();

        barEntries.add(new BarEntry(1, number[0]));
        barEntries.add(new BarEntry(2, number[1]));

        BarDataSet dataSet = new BarDataSet(barEntries,"Calorie consumed and burned");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        BarData theData = new BarData(dataSet);

        BarChart chart = (BarChart) vReportUnit.findViewById(R.id.barChart);
        chart.animateY(3000);
        chart.setData(theData);

        chart.setTouchEnabled(true);
        chart.setDragEnabled(true);

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


    private class GetPieData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String...params){
            Integer id = Integer.parseInt(params[0]);
            return RestClient.getPiechartDate(id, params[1]); }
        @Override
        protected void onPostExecute (String recordPassword){
        }
    }

    private class GetBarData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String...params){
            Integer id = Integer.parseInt(params[0]);
            return RestClient.getBarchartData(id, params[1], params[2]); }
        @Override
        protected void onPostExecute (String recordPassword){
        }
    }
}