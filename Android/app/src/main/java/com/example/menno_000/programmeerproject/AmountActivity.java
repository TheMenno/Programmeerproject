package com.example.menno_000.programmeerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

import static java.lang.Integer.valueOf;

public class AmountActivity extends AppCompatActivity implements DataRequest.Callback {

    String name, api_id, retrievedMeal;
    Integer calories;
    TextView nameView, measureView;
    StoredFoodDatabase storedFoodDatabase;
    EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amount);

        // Intent
        Intent intent = getIntent();
        api_id = (String) intent.getSerializableExtra("id");
        retrievedMeal = (String) intent.getSerializableExtra("meal");

        // Data request
        DataRequest request = new DataRequest(this);
        request.getData(this, api_id);

        // Views
        nameView = findViewById(R.id.name_amount);
        measureView = findViewById(R.id.measure);
        Button addButton = findViewById(R.id.amountAddButton);
        edit = findViewById(R.id.editText);

        // Listeners
        addButton.setOnClickListener(new ButtonClickListener());

        // Database
        storedFoodDatabase = StoredFoodDatabase.getInstance(getApplicationContext());
    }


    @Override
    public void gotData(JSONObject response) {

        try {
            JSONObject raw_info = response.getJSONObject("desc");
            JSONArray raw_nutrients = response.getJSONArray("nutrients");
            JSONObject raw_values = raw_nutrients.getJSONObject(1);

            name = (String) raw_info.get("name");
            calories = raw_values.getInt("value");

            JSONArray measures = raw_values.getJSONArray("measures");

            if (measures.get(0) instanceof String) {

                String label = (String) measures.get(0);
                String qty = (String) measures.get(3);
                String amount = (String) measures.get(4);
                measureView.setText(qty + " " + label + " equals " + amount + " calories");
            } else {
                JSONObject object = (JSONObject) measures.get(0);

                String label = (String) object.get("label");
                Double qty = (Double) object.get("qty");
                String amount = (String) object.get("value");
                measureView.setText(qty.toString() + " " + label + " equals " + amount + " calories");
            }

            nameView.setText(name);
        } catch (JSONException e) {
            // Error message
            e.printStackTrace();
            this.gotDataError("No results found, try something else!");
        }
    }

    // Error message
    @Override
    public void gotDataError(String message) {

        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }


    // Listener for the buttons, go to the next/previous screen
    public class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            if (edit.getText().toString().equals("")) {
                Toast.makeText(AmountActivity.this, "Please enter something", Toast.LENGTH_SHORT).show();
            } else {
                Integer amount = valueOf(edit.getText().toString());

                storedFoodDatabase.insert(
                        new Food(
                                valueOf(api_id),
                                name,
                                calories * amount,
                                amount,
                                retrievedMeal));
                goToNext();
            }
        }
    }

    public void goToNext() {

        // Give info to new view
        Intent intent = new Intent(AmountActivity.this, MealActivity.class);
        intent.putExtra("clickedMeal", retrievedMeal);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
