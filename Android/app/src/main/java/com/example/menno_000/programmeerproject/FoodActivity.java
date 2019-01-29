package com.example.menno_000.programmeerproject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import static java.lang.String.valueOf;

public class FoodActivity extends AppCompatActivity implements FoodRequest.Callback {

    EditText editText;
    ArrayList<Integer> responseId = new ArrayList<>();
    ArrayList<String> responseName = new ArrayList<>();
    String retrievedMeal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);

        // Intent
        Intent intent = getIntent();
        retrievedMeal = (String) intent.getSerializableExtra("meal");

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
    public void gotFood(ArrayList<ArrayList> response) {

        Integer size = response.size();
        for(int l=0; l<size-1; l++){
            ArrayList array = response.get(l);

            Integer id = (Integer) array.get(0);
            responseId.add(id);

            String name = (String) array.get(1);
            responseName.add(name);
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_list_item_1, responseName);

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
            String name = responseName.get(i);
            String id = valueOf(responseId.get(i));

            toNext(id);
        }
    }


    // Listener for the buttons, go to the next/previous screen
    public class SearchClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Request();
        }
    }


    public void toNext(String id) {
        Intent intent = new Intent(this, AmountActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("meal", retrievedMeal);
        startActivity(intent);
    }


    public void Request() {
        FoodRequest request = new FoodRequest(this);
        request.getFood(this, editText.getText().toString());
    }
}
