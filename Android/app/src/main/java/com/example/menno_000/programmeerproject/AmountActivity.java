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

import java.util.ArrayList;

public class AmountActivity extends AppCompatActivity implements DataRequest.Callback {

    String retrievedId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        // Intent
        Intent intent = getIntent();
        retrievedId = (String) intent.getSerializableExtra("id");

        // Data request
        DataRequest request = new DataRequest(this);
        request.getData(this, retrievedId);
    }


    // Listener for the "Get started!" button, go to the next screen
    // Create a game when the food items are loaded successfully
    @Override
    public void gotData(JSONArray response) {

        TextView textView = findViewById(R.id.text);
        textView.setText(response.toString());
    }

    // Error message
    @Override
    public void gotDataError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
