package com.example.menno_000.programmeerproject;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    StoredFoodDatabase storedFoodDatabase;
    Class activity;
    TextView dateView;
    ArrayList<ArrayList> listItems = new ArrayList<>();
    Integer totalCal;
    DatePickerDialog dpd;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database
        storedFoodDatabase = StoredFoodDatabase.getInstance(getApplicationContext());

        // ListView Adapter
        PrepareAdapter();
        ListView listView = findViewById(R.id.main_list);
        MainAdapter adapter = new MainAdapter(this, R.layout.entryrow_main, listItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new MealItemClickListener());

        // Buttons
        Button graphButton = findViewById(R.id.mainGraphButton);
        Button userButton = findViewById(R.id.mainUserButton);
        TextView datePicker = findViewById(R.id.mainDatePicker);


        // Listeners
        graphButton.setOnClickListener(new MainActivity.ButtonClickListener());
        userButton.setOnClickListener(new MainActivity.ButtonClickListener());
        datePicker.setOnClickListener(new MainActivity.DatePickListener());

        // Date
        dateView            = findViewById(R.id.mainDatePicker);
        Date date           = Calendar.getInstance().getTime();
        String day          = (String) DateFormat.format("dd",   date);
        String monthString  = (String) DateFormat.format("M",  date);
        String year         = (String) DateFormat.format("yy", date);
        dateView.setText(day + "-" + monthString + "-" + year);

        // Total
        TextView totalView = findViewById(R.id.main_total);
        totalView.setText("Total: " + totalCal + " calories");
    }


    // Listener for the buttons, go to the next/previous screen
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


    // Listener for the buttons, go to the next/previous screen
    public class DatePickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {

            calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int month = calendar.get(Calendar.MONTH);
            int year = calendar.get(Calendar.YEAR);

            dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                    TextView tempDatePicker = findViewById(R.id.mainDatePicker);
                    tempDatePicker.setText(d + "-" + m+1 + "-" + y);
                }
            }, year, month, day);
            dpd.show();
        }
    }


    // A listener that creates a new view after a meal is chosen from the list
    private class MealItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            // Retrieve chosen meal
            ArrayList clickedListItem = (ArrayList) adapterView.getItemAtPosition(i);
            String clickedMeal = clickedListItem.get(0).toString();

            // Give info to new view
            Intent intent = new Intent(MainActivity.this, MealActivity.class);
            intent.putExtra("clickedMeal", clickedMeal);
            startActivity(intent);
        }
    }


    // Retrieves data to fill in the adapter
    public void PrepareAdapter() {

        Integer breakfastCal = 0;
        Integer lunchCal = 0;
        Integer dinnerCal = 0;
        Integer snacksCal = 0;

        final Cursor cursor = storedFoodDatabase.selectAll();
        if (cursor != null) {
            if (cursor.moveToFirst()){
                do{
                    String meal = cursor.getString( cursor.getColumnIndex("meal") );
                    Integer calories = cursor.getInt( cursor.getColumnIndex("calories") );
                    Integer amount = cursor.getInt( cursor.getColumnIndex("amount") );

                    switch(meal) {

                        case "Breakfast":
                            breakfastCal += calories;
                            break;
                        case "Lunch":
                            lunchCal += calories;
                            break;
                        case "Dinner":
                            dinnerCal += calories;
                            break;
                        case "Snacks":
                            snacksCal += calories;
                            break;
                    }
                } while(cursor.moveToNext());
            }
            cursor.close();
        }

        totalCal = breakfastCal + lunchCal + dinnerCal + snacksCal;

        listItems.add(new ArrayList<>(Arrays.asList("Breakfast", breakfastCal.toString())));
        listItems.add(new ArrayList<>(Arrays.asList("Lunch", lunchCal.toString())));
        listItems.add(new ArrayList<>(Arrays.asList("Dinner", dinnerCal.toString())));
        listItems.add(new ArrayList<>(Arrays.asList("Snacks", snacksCal.toString())));
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