package com.example.menno_000.programmeerproject;

import android.content.Context;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class DataRequest implements Response.Listener<JSONObject>, Response.ErrorListener {

    private Context context;
    private Callback callback;

    public interface Callback {
        void gotData(JSONArray data);
        void gotDataError(String message);
    }


    // Constructor
    public DataRequest(Context context) {

        this.context = context;
    }

    // Connect to the api
    public void getData(Callback call, String id) {

        // Set up a queue
        RequestQueue queue = Volley.newRequestQueue(context);

        // Create the url
        String url = "https://api.nal.usda.gov/ndb/V2/reports?ndbno=" + id + "&type=b&format=json&api_key=nGHKa4ZmkXSTIiLWoLIzQKVs3jHIWAcvH0l8OGGI";

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
        callback.gotDataError(error.getMessage());
    }


    // Acts when API can be accessed
    @Override
    public void onResponse(JSONObject response) {
        try {

            // Open the results
            JSONArray raw_JSON = response.getJSONArray("foods");
            //JSONObject raw_JSONObject = JSONArray.getJSONObject("food");

            callback.gotData(raw_JSON);
        } catch (JSONException e) {
            // Error message
            e.printStackTrace();
            callback.gotDataError("No results found, try something else!");
        }
    }

}
