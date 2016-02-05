package com.example.kunalpatel.crunchtime;

import android.media.Image;

import java.text.DecimalFormat;

/**
 * Created by Kunal Patel on 2/4/2016.
 */
public class Exercise {
    public static final String TYPE_REPS = "reps";
    public static final String Type_MINUTES = "mins";

    private String name, measurementType;
    public String key;
    private double amountFor100Cals;
    private double count = 0;

    public Exercise() {
        this.measurementType = TYPE_REPS;
        this.amountFor100Cals = 100;
    }
    public Exercise (String key, String name, String measurementType, double amountFor100Cals) {
        this.key = key;
        this.name = name;
        this.measurementType = measurementType;
        this.amountFor100Cals = amountFor100Cals;
    }

    public String getName() {
        return name;
    }

    public String getMeasurementType() {
        return measurementType;
    }

    public double getAmountFor100Cals() {
        return amountFor100Cals;
    }

    public double getCount() {
       return  Math.round(count * 10.0) / 10.0;
    }

    public void setCount(double count) {
        this.count = count;
    }

}
