package com.example.menno_000.programmeerproject;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MealAdapter extends ResourceCursorAdapter {
    public MealAdapter(Context context, Cursor cursor) {
        super(context, R.layout.entryrow_meal, cursor);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        String food = cursor.getString(cursor.getColumnIndex("product"));
        String calories = cursor.getString(cursor.getColumnIndex("calories"));;

        TextView mealText = view.findViewById(R.id.food_row);
        TextView calorieText = view.findViewById(R.id.calorie_row);

        mealText.setText(food);
        calorieText.setText(calories + " cal.");
    }
}