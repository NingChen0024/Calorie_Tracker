package com.example.ning.assignmnet3;

import java.util.Date;

public class NewReport {

    private Integer reportId;
    private Date reportDate;
    private int totalConsumed;
    private int totalBurned;
    private int totalSteps;
    private int calorieGoal;
    private Users userId;

    public NewReport(Integer reportId, Date reportDate, int totalConsumed, int totalBurned,
                     int totalSteps, int calorieGoal, Users userId){

        this.reportId = reportId;
        this.reportDate = reportDate;
        this.totalConsumed = totalConsumed;
        this.totalBurned = totalBurned;
        this.totalSteps = totalSteps;
        this.calorieGoal = calorieGoal;
        this.userId = userId;

    }

}
