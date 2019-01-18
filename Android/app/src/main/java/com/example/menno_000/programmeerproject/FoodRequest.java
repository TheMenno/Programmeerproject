package com.example.menno_000.programmeerproject;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FoodRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback callback;

    // A callback to the previous activity
    public interface Callback {
        void gotFood(ArrayList<String> food);
        void gotFoodError(String message);
    }


    // Constructor
    public FoodRequest(Context context) {

        this.context = context;
    }

    // Connect to the api
    public void getFood(Callback call, String search) {

        // Set up a queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Create the data request
        String offset = "0";
        String amount = "200";
        String key = "nGHKa4ZmkXSTIiLWoLIzQKVs3jHIWAcvH0l8OGGI";

        // Create the url
        String url ="https://api.nal.usda.gov/ndb/search/?format=json&q=" + search + "&max=" +
                amount + "&offset=" + offset + "&api_key=" + key;

        // Create the request
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null,
                this, this);

        // Add the request to the queue
        queue.add(jsonObjectRequest);

        callback = call;
    }

    // Acts when API can't be accessed
    @Override
    public void onErrorResponse(VolleyError error) {
        callback.gotFoodError(error.getMessage());
    }


    // Acts when API can be accessed
    @Override
    public void onResponse(JSONObject response) {
        try {
            // Open the results
            JSONObject raw_JSONObject = response.getJSONObject("list");
            JSONArray raw_JSON = raw_JSONObject.getJSONArray("item");

            // Set up a container
            ArrayList<String> raw_Strings = new ArrayList<>();

            // Separate the questions
            for (int i = 0; i < raw_JSON.length(); i++) {
                JSONObject raw_food = raw_JSON.getJSONObject(i);
                String manufactor = raw_food.getString("manu");

                if(manufactor.equals("none")) {
                    String name = raw_food.getString("name");

                    if(name.equals(name.toUpperCase())) {
                        continue;
                    } else {
                        raw_Strings.add(name);
                    }
                }
            }

            callback.gotFood(raw_Strings);
        } catch (JSONException e) {
            // Error message
            e.printStackTrace();
            callback.gotFoodError("JSONException");
        }
    }

}
