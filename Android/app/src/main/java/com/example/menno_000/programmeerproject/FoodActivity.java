package com.example.menno_000.programmeerproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.security.auth.callback.Callback;

import static java.lang.String.join;

public class FoodActivity extends AppCompatActivity implements FoodRequest.Callback {

    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        //Button backButton = findViewById(R.id.foodBackButton);
        Button searchButton = findViewById(R.id.foodSearchButton);
        editText = findViewById(R.id.editText);
        ListView listView = findViewById(R.id.foodList);

        listView.setOnItemClickListener(new FoodActivity.FoodItemClickListener());
        //backButton.setOnClickListener(new FoodActivity.ButtonClickListener());
        searchButton.setOnClickListener(new FoodActivity.SearchClickListener());
    }


    /*
    // Listener for the back button, return to the previous screen
    public class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            goToNext();
        }
    }
    */

    // Listener for the "Get started!" button, go to the next screen
    // Create a game when the food items are loaded successfully
    @Override
    public void gotFood(ArrayList<String> response) {
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, response);

        ListView listView = findViewById(R.id.foodList);
        listView.setAdapter(arrayAdapter);
    }


    // Error message
    @Override
    public void gotFoodError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    // A listener that creates a new view after an item is chosen from the list
    private class FoodItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Retrieve chosen item
            String clickedListItem = (String) adapterView.getItemAtPosition(i);
            //String clickedItem = clickedListItem.get(0).toString();

            Toast.makeText(FoodActivity.this, clickedListItem, Toast.LENGTH_SHORT).show();
        }
    }


    // Listener for the buttons, go to the next/previous screen
    public class SearchClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            toNext();
        }
    }


    public void toNext() {
        FoodRequest request = new FoodRequest(this);
        request.getFood(this, editText.getText().toString());
    }
}
