package com.example.menno_000.programmeerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.menno_000.programmeerproject.FoodRequest;
import com.example.menno_000.programmeerproject.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AmountActivity extends AppCompatActivity implements DataRequest.Callback {

    String name;
    Integer calories;
    String api_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        // Intent
        Intent intent = getIntent();
        api_id = (String) intent.getSerializableExtra("id");

        // Data request
        DataRequest request = new DataRequest(this);
        request.getData(this, api_id);
    }


    // Listener for the "Get started!" button, go to the next screen
    // Create a game when the food items are loaded successfully
    @Override
    public void gotData(JSONObject response) {

        try {
            JSONObject raw_info = response.getJSONObject("desc");
            name = (String) raw_info.get("name");

            JSONArray raw_nutrients = response.getJSONArray("nutrients");
            JSONObject raw_values = raw_nutrients.getJSONObject(1);
            calories = raw_values.getInt("value");
        } catch (JSONException e) {
            // Error message
            e.printStackTrace();
            this.gotDataError("No results found, try something else!");
        }

        TextView nameView = findViewById(R.id.name_amount);
        TextView calorieView = findViewById(R.id.text);
        nameView.setText(name);
        calorieView.setText(calories.toString());
    }

    // Error message
    @Override
    public void gotDataError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
