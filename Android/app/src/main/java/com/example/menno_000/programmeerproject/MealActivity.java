package com.example.menno_000.programmeerproject;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.Integer.valueOf;

public class MealActivity extends AppCompatActivity {

    StoredFoodDatabase storedFoodDatabase;
    Class activity;
    String retrievedMeal;
    Integer cals = 0;
    TextView mealView, calView;
    ArrayList<ArrayList> foodList = new ArrayList();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        // Database
        storedFoodDatabase = StoredFoodDatabase.getInstance(getApplicationContext());

        // Intent
        Intent intent = getIntent();
        retrievedMeal = (String) intent.getSerializableExtra("clickedMeal");

        // Views
        mealView = findViewById(R.id.meal_name);
        calView = findViewById(R.id.meal_total);
        Button addButton = findViewById(R.id.mealAddButton);

        // Initialise views
        mealView.setText(retrievedMeal);
        Cursor cursor = storedFoodDatabase.selectMeal(retrievedMeal);
        while (cursor.moveToNext()) {
            cals += valueOf(cursor.getString(3));
        }
        calView.setText("Total: " + cals.toString() + " calories");

        // Adapter
        listView = findViewById(R.id.meal_list);
        MealAdapter adapter = new MealAdapter(this, storedFoodDatabase.selectMeal(retrievedMeal));
        listView.setAdapter(adapter);

        // Listeners
        listView.setOnItemLongClickListener(new OnLongItemClickListener());
        addButton.setOnClickListener(new MealActivity.ButtonClickListener());
    }


    // Deletes entry on long click
    private class OnLongItemClickListener implements AdapterView.OnItemLongClickListener {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Delete chosen entry
            storedFoodDatabase.delete(l);

            // Update the listview
            MealAdapter adapter = new MealAdapter(MealActivity.this, storedFoodDatabase
                    .selectMeal(retrievedMeal));
            listView.setAdapter(adapter);

            // Reset the total calorie amount
            Cursor cursor = storedFoodDatabase.selectMeal(retrievedMeal);
            while (cursor.moveToNext()) {
                cals += valueOf(cursor.getString(3));
            }
            calView.setText("Total: " + cals.toString() + " calories");

            // Notify the user
            Toast.makeText(MealActivity.this, "Item was deleted", Toast.LENGTH_SHORT).show();

            return false;
        }
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


    // Go to the next screen
    public void goToNext(String clickedButton) {

        switch (clickedButton) {
            case("back"):
                activity = MainActivity.class;
                break;
            case("add"):
                activity = FoodActivity.class;
                break;
        }

        Intent intent = new Intent(this, activity);
        intent.putExtra("meal", retrievedMeal);
        startActivity(intent);
    }
}
