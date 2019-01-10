package com.example.menno_000.programmeerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class FoodActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        Button backButton = findViewById(R.id.foodBackButton);
        Button searchButton = findViewById(R.id.foodSearchButton);

        backButton.setOnClickListener(new FoodActivity.ButtonClickListener());
        //searchButton.setOnClickListener(new FoodActivity.ButtonClickListener());
    }


    // Listener for the back button, return to the previous screen
    public class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            goToNext();
        }
    }


    // Go to the next screen
    public void goToNext() {

        Intent intent = new Intent(this, MealActivity.class);
        startActivity(intent);
    }
}
