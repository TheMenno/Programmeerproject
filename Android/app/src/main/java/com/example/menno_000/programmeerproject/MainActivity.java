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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    StoredFoodDatabase storedFoodDatabase;
    Class activity;
    TextView datePickView, totalView;
    ListView listView;
    ArrayList<ArrayList> listItems = new ArrayList<>();
    DatePickerDialog dpd;
    Calendar calendar;
    Integer totalCal, day, month, year;
    String adjustedDay, adjustedMonth;
    MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Database
        storedFoodDatabase = StoredFoodDatabase.getInstance(getApplicationContext());

        // Calendar
        calendar = Calendar.getInstance();
        day = calendar.get(Calendar.DAY_OF_MONTH);
        month = calendar.get(Calendar.MONTH) + 1;
        year = calendar.get(Calendar.YEAR);

        // Views
        Button graphButton = findViewById(R.id.mainGraphButton);
        Button userButton = findViewById(R.id.mainUserButton);
        datePickView = findViewById(R.id.mainDatePicker);
        listView = findViewById(R.id.main_list);
        totalView = findViewById(R.id.main_total);

        // ListView Adapter
        PrepareAdapter();
        adapter = new MainAdapter(this, R.layout.entryrow_main, listItems);
        listView.setAdapter(adapter);

        // Listeners
        graphButton.setOnClickListener(new MainActivity.ButtonClickListener());
        userButton.setOnClickListener(new MainActivity.ButtonClickListener());
        datePickView.setOnClickListener(new MainActivity.DatePickListener());
        listView.setOnItemClickListener(new MealItemClickListener());
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

            dpd = new DatePickerDialog(MainActivity.this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int y, int m, int d) {

                    day = d;
                    month = m + 1;
                    year = y;

                    PrepareAdapter();
                    adapter.notifyDataSetChanged();
                }
            }, year, month - 1, day);
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
                    Timestamp date = (Timestamp.valueOf(cursor.getString( cursor.getColumnIndex("date") ) ) );

                    String dataYear = date.toString().substring(0,4);
                    String dataMonth = date.toString().substring(5,7);
                    String dataDay = date.toString().substring(8,10);

                    if(day.toString().length() == 1) {
                        adjustedDay = "0" + day.toString();
                    } else {
                        adjustedDay = day.toString();
                    }

                    if(month.toString().length() == 1) {
                        adjustedMonth = "0" + month.toString();
                    } else {
                        adjustedMonth = month.toString();
                    }

                    if (( dataYear.equals( year.toString() )) &&
                        ( dataMonth.equals( adjustedMonth )) &&
                        ( dataDay.equals( adjustedDay ))) {

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
                    }
                } while(cursor.moveToNext());
            }
            cursor.close();
        }

        datePickView.setText(adjustedDay + "-" + adjustedMonth + "-" + year.toString());

        totalCal = 0 + breakfastCal + lunchCal + dinnerCal + snacksCal;
        totalView.setText("Total: " + totalCal.toString() + " calories");

        listItems.clear();

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


    // Overriding app settings so progress will be saved between swapping views
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("day", day);
        outState.putInt("month", month);
        outState.putInt("year", year);
    }

    // Overriding app settings so progress will be restored between swapping views
    @Override
    public void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);

        day = inState.getInt("day");
        month = inState.getInt("month");
        year = inState.getInt("year");

        PrepareAdapter();
        adapter.notifyDataSetChanged();
    }
}