package com.example.menno_000.programmeerproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MealActivity extends AppCompatActivity {

    StoredFoodDatabase storedFoodDatabase;
    Class activity;
    String retrievedMeal;
    Integer totalCal = 0;
    TextView mealView, totalView;
    ArrayList<ArrayList> foodList = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        // Database
        storedFoodDatabase = StoredFoodDatabase.getInstance(getApplicationContext());

        // Intent
        Intent intent = getIntent();
        retrievedMeal = (String) intent.getSerializableExtra("clickedMeal");

        // Adapter
        PrepareAdapter();
        ListView listView = findViewById(R.id.meal_list);
        MealAdapter adapter = new MealAdapter(this, R.layout.entryrow_meal, foodList);
        listView.setAdapter(adapter);

        // Views
        mealView = findViewById(R.id.meal_name);
        totalView = findViewById(R.id.meal_total);
        Button backButton = findViewById(R.id.mealBackButton);
        Button addButton = findViewById(R.id.mealAddButton);

        // Initialise views
        mealView.setText(retrievedMeal);
        totalView.setText("Total: " + totalCal.toString() + " calories");

        // Listeners
        backButton.setOnClickListener(new MealActivity.ButtonClickListener());
        addButton.setOnClickListener(new MealActivity.ButtonClickListener());
    }


    // Listener for the buttons, go to the next/previous screen
    public class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case(R.id.mealBackButton):
                    goToNext("back");
                    break;
                case(R.id.mealAddButton):
                    goToNext("add");
                    break;
            }
        }
    }


    public void PrepareAdapter() {

        final Cursor cursor = storedFoodDatabase.selectAll();
        if (cursor != null) {
            if (cursor.moveToFirst()){
                do{
                    String meal = cursor.getString( cursor.getColumnIndex("meal") );
                    if (meal.equals(retrievedMeal)) {
                        String id = cursor.getString( cursor.getColumnIndex("_id") );
                        String name = cursor.getString( cursor.getColumnIndex("product") );
                        Integer cals = cursor.getInt( cursor.getColumnIndex("calories") );

                        foodList.add( new ArrayList<>( Arrays.asList(id, name, cals.toString()) ) );
                        totalCal += cals;
                    }
                } while(cursor.moveToNext());
            }
            cursor.close();
        }
    }


    // Go to the next screen
    public void goToNext(String clickedButton) {

        switch (clickedButton) {
            case("back"):
                activity = MainActivity.class;
                break;
            case("add"):
                // do something
                break;
        }

        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
