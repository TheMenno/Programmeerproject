package com.example.menno_000.programmeerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MealActivity extends AppCompatActivity {

    Class activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal);

        Button backButton = findViewById(R.id.mealBackButton);
        Button addButton = findViewById(R.id.mealAddButton);

        backButton.setOnClickListener(new MealActivity.ButtonClickListener());
        addButton.setOnClickListener(new MealActivity.ButtonClickListener());
    }


    // Listener for the "Get started!" button, go to the next screen
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
                //activity = FoodActivity.class;
                break;
        }

        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
