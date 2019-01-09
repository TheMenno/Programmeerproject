package com.example.menno_000.programmeerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class UserActivity extends AppCompatActivity {

    Class activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Button backButton = findViewById(R.id.userBackButton);
        Button editButton = findViewById(R.id.userEditButton);

        backButton.setOnClickListener(new UserActivity.ButtonClickListener());
        editButton.setOnClickListener(new UserActivity.ButtonClickListener());
    }


    // Listener for the "Get started!" button, go to the next screen
    public class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case(R.id.userBackButton):
                    goToNext("back");
                    break;
                case(R.id.userEditButton):
                    goToNext("edit");
                    break;
            }
        }
    }


    // Go to the next screen
    public void goToNext(String clickedButton) {

        switch (clickedButton) {
            case("back"):
                activity = MainActivity.class;
                break;
            case("edit"):
                // Do something
                break;
        }

        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }
}
