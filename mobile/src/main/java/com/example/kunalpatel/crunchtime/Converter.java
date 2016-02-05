package com.example.kunalpatel.crunchtime;

/**
 * Created by Kunal Patel on 2/4/2016.
 */
public class Converter {

    public static double calculateCaloriesBurned (Exercise exercise, int repsOrMinutes) {
        double caloriesBurned = 100/exercise.getAmountFor100Cals() * repsOrMinutes;
        return caloriesBurned;
    }

    public static double calculateRepsForCalories(Exercise exercise, double caloriesDesired) {
        double amountFor100Cals = exercise.getAmountFor100Cals();
        return amountFor100Cals/100 * caloriesDesired;

    }

    public static double convertReps(Exercise from, Exercise to, int repsOrMinutes) {
        // Calories burned by FROM = calories desired by TO
        // reps/100 cal
        double caloriesBurned = calculateCaloriesBurned(from, repsOrMinutes);

        return calculateRepsForCalories( to, caloriesBurned);

    }

}
