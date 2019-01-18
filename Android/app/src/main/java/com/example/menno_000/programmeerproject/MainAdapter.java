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

public class MainAdapter extends ArrayAdapter {
    public MainAdapter(Context context, int resource, @NonNull ArrayList<ArrayList> objects) {
        super(context, R.layout.entryrow_main, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // A new view must be inflated
        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.entryrow_main, parent, false);
        }

        // Access the right student in the list
        ArrayList currentMeal = (ArrayList) getItem(position);

        // Make changes to the convertView, such as displaying a certain text
        TextView mealText = convertView.findViewById(R.id.meal_row);
        TextView calorieText = convertView.findViewById(R.id.calorie_row);

        mealText.setText(currentMeal.get(0).toString());
        calorieText.setText(currentMeal.get(1).toString() + " calories");

        return convertView;
    }
}