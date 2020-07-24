package com.example.ning.assignmnet3;

public class Foods {
    private Integer foodId;
    private String foodName;
    private String category;
    private int calorieAmount;
    private String servingUnit;
    private double servingAmount;
    private int fat;

    public Foods(Integer foodId, String foodName, String category,
                 int calorieAmount, String servingUnit, double servingAmount, int fat){

        this.foodId = foodId;
        this.foodName = foodName;
        this.category = category;
        this.calorieAmount = calorieAmount;
        this.servingAmount = servingAmount;
        this.servingUnit = servingUnit;
        this.fat = fat;


    }
}
