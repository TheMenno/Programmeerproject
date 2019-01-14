package com.example.menno_000.programmeerproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements FoodRequest.Callback {

    Class activity;
    ListView listView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FoodRequest request = new FoodRequest(this);
        request.getFood(this);

        listView = findViewById(R.id.mainList);
        textView = findViewById(R.id.mainDatePicker);
        Button graphButton = findViewById(R.id.mainGraphButton);
        Button userButton = findViewById(R.id.mainUserButton);

        //listView.setOnItemClickListener(new MealItemClickListener());
        graphButton.setOnClickListener(new MainActivity.ButtonClickListener());
        userButton.setOnClickListener(new MainActivity.ButtonClickListener());
    }


    // Create a game when the food items are loaded successfully
    @Override
    public void gotFood(ArrayList<String> response) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, response);

        listView.setAdapter(arrayAdapter);
    }


    // Error message
    @Override
    public void gotFoodError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    // Listener for the buttons, go to the next/previous screen
    public class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case(R.id.mainGraphButton):
                    goToNext("graph");
                    break;
                case(R.id.mainUserButton):
                    goToNext("user");
                    break;
            }
        }
    }


    // A listener that creates a new view after a meal is chosen from the list
    private class MealItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Retrieve chosen meal
            String clickedMeal = (String) adapterView.getItemAtPosition(i);

            // Give info to new view
            Intent intent = new Intent(MainActivity.this, MealActivity.class);
            intent.putExtra("clickedMeal", clickedMeal);
            startActivity(intent);
        }
    }


    // Go to the next screen
    public void goToNext(String clickedButton) {

        switch (clickedButton) {
            case("graph"):
                activity = GraphActivity.class;
                break;
            case("user"):
                activity = UserActivity.class;
                break;
        }

        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}