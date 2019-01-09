package com.example.menno_000.programmeerproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class GraphActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Button backButton = findViewById(R.id.graphBackButton);
        backButton.setOnClickListener(new GraphActivity.ButtonClickListener());
    }


    // Listener for the "Get started!" button, go to the next screen
    public class ButtonClickListener implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            goToNext();
        }
    }


    // Go to the next screen
    public void goToNext() {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
