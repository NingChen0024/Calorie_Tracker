package com.example.ning.assignmnet3;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import java.util.Date;

@Entity
public class Report {

    @PrimaryKey(autoGenerate = true)
    public Integer reportId;

/*
    @ColumnInfo(name = "ReportDate")
    public Date reportDate;
*/

    @ColumnInfo(name = "totalConsumed")
    public int totalConsumed;

    @ColumnInfo(name = "totalSteps")
    public int totalSteps;

    @ColumnInfo(name = "totalBurned")
    public int totalBurned;

    @ColumnInfo(name = "calorieGoal")
    public int calorieGoal;

  /*  @ColumnInfo(name = "userId")
    public Users userId;*/




    public Report( int totalConsumed, int totalSteps, int totalBurned,
                  int calorieGoal) {

        //this.reportDate = reportDate;
        this.totalConsumed = totalConsumed;
        this.totalSteps = totalSteps;
        this.totalBurned = totalBurned;
        this.calorieGoal = calorieGoal;
        //this.userId = userId;
    }

    public Integer getReportId(){
        return reportId;
    }

    public int getTotalConsumed() {
        return totalConsumed;
    }
    public int getTotalSteps() {
        return totalSteps;
    }
    public int getTotalBurned() {
        return totalBurned;
    }
    public int getCalorieGoal() {
        return calorieGoal;
    }

    public void setTotalConsumed(int totalConsumed){
        this.totalConsumed = totalConsumed;
    }
    public void setTotalSteps(int totalSteps){
        this.totalSteps = totalSteps;
    }
    public void setTotalBurned(int totalBurned){
        this.totalBurned = totalBurned;
    }
    public void setCalorieGoal(int calorieGoal){
        this.calorieGoal = calorieGoal;
    }

}
