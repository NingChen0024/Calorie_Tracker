package com.example.ning.assignmnet3;

import java.util.Date;

public class Consumption {
    private Integer counsumptionId;
    private Date consumeDate;
    private int qtyofserving;
    private Foods foodId;
    private Users userId;

    public Consumption(Integer consumptionId, Date consumptionDate, int qtyofserving, Foods foodId, Users userId){
        this.counsumptionId = consumptionId;
        this.consumeDate= consumptionDate;
        this.qtyofserving = qtyofserving;
        this.foodId = foodId;
        this.userId = userId;
    }


}
