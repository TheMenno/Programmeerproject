package com.example.menno_000.programmeerproject;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    Class activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView listview = findViewById(R.id.mainList);
        Button graphButton = findViewById(R.id.mainGraphButton);
        Button userButton = findViewById(R.id.mainUserButton);

        //ListView.setOnItemClickListener(new MealItemClickListener());
        graphButton.setOnClickListener(new MainActivity.ButtonClickListener());
        userButton.setOnClickListener(new MainActivity.ButtonClickListener());
    }


    // Listener for the "Get started!" button, go to the next screen
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
