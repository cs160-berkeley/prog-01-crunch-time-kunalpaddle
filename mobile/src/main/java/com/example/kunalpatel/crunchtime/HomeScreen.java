package com.example.kunalpatel.crunchtime;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.HashMap;

public class HomeScreen extends AppCompatActivity {

    private ArrayList<Exercise> exercises;
    private HashMap<String, Exercise> exerciseHashMap = new HashMap<String, Exercise>();
    private Exercise toConvert;
    private Exercise currentExcercise = new Exercise();

    private TextView exerciseType, caloriesLabel;
    private EditText repCount, caloriesEditText;
    private ListView exerciseList;
    private Spinner exerciseSelector;
    private ExerciseListAdapter listAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("----------------------------------------------onCreate");
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        repCount = (EditText) findViewById(R.id.repCount);
        exerciseType = (TextView) findViewById(R.id.exerciseTypeTextView);
        caloriesLabel = (TextView) findViewById(R.id.caloriesLabel);
        exerciseSelector = (Spinner) findViewById(R.id.exerciseSpinner);
        exerciseList = (ListView) findViewById(R.id.exerciseListView);
        caloriesEditText = (EditText) findViewById(R.id.caloriesEditText);

        exercises = makeExercises();
        listAdapter = new ExerciseListAdapter(this, exercises);
        exerciseList.setAdapter(listAdapter);

        repCount.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                refreshUI(false);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        //Set up drop-down selector

        ArrayList<String> exerciseNames = new ArrayList<String>();
        for (Exercise ex: exercises){
            String name = ex.getName();
            exerciseNames.add(name);
            exerciseHashMap.put(name, ex);
        }
        exerciseSelector.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                Exercise toConvert = exerciseHashMap.get(item);
                setCurrentExcercise(toConvert);
                exerciseType.setText(currentExcercise.getMeasurementType()+" of ");
                refreshUI(false);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item, exerciseNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        exerciseSelector.setAdapter(spinnerAdapter);


        caloriesEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                refreshUI(true);
            }
        });
    }

    public void setCurrentExcercise(Exercise e) {
        currentExcercise = e;
    }

    public void refreshUI(boolean usingCalories) {
        // Calculate calories burned
        try {

            //Update table entries
            if(usingCalories) {
                int caloriesDesired = Integer.parseInt(caloriesEditText.getText().toString());
                for (Exercise ex: exercises) {
                    ex.setCount(Converter.calculateRepsForCalories(ex, caloriesDesired));
                }
                repCount.getText().clear();
            }
            else {
                int numReps = Integer.parseInt(repCount.getText().toString());
                double caloriesBurned = Converter.calculateCaloriesBurned(currentExcercise, numReps);
                caloriesBurned = Math.round(caloriesBurned*10.0)/10.0;
                caloriesEditText.setText(String.valueOf(caloriesBurned));

                for (Exercise other: exercises) {
                    other.setCount(Converter.convertReps(currentExcercise,other,numReps));
                }
            }
            //Refresh table
            reloadExerciseList();
        }
        catch (Exception e) {

        }

    }

    private void reloadExerciseList() {
        listAdapter.notifyDataSetChanged();
    }

    public ArrayList<Exercise> makeExercises() {
        ArrayList exercises = new ArrayList<Exercise>();
        Exercise pushUps = new Exercise("push_ups","Push-Ups",Exercise.TYPE_REPS,350);
        Exercise sitUps = new Exercise("crunches","Situps ",Exercise.TYPE_REPS,250);
        Exercise jumpingJacks = new Exercise("jumping_jacks","Jumping Jacks",Exercise.Type_MINUTES,10);
        Exercise jogging = new Exercise("jogging","Jogging",Exercise.Type_MINUTES,12);
        Exercise cycling = new Exercise("cycling","Cycling",Exercise.Type_MINUTES,12);
        Exercise legLifts = new Exercise("leg_lift","Leg Lifts",Exercise.Type_MINUTES,25);
        Exercise planking = new Exercise("planking","Plank",Exercise.Type_MINUTES,25);
        Exercise pullUps = new Exercise("pull_ups","Pull-Ups",Exercise.TYPE_REPS,100);
        Exercise squats = new Exercise("squatting","Squats",Exercise.TYPE_REPS,225);
        Exercise stairClimbing = new Exercise("stair_climbing","Stair Climbing",Exercise.Type_MINUTES,15);
        Exercise walking = new Exercise("walking","Walking",Exercise.Type_MINUTES,20);
        Exercise swimming = new Exercise("swimming", "Swimming", Exercise.Type_MINUTES, 13);

        exercises.add(planking);
        exercises.add(cycling);
        exercises.add(pullUps);
        exercises.add(walking);
        exercises.add(swimming);
        exercises.add(stairClimbing);
        exercises.add(squats);
        exercises.add(pushUps);
        exercises.add(sitUps);
        exercises.add(jumpingJacks);
        exercises.add(jogging);
        exercises.add(legLifts);



        return  exercises;
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
