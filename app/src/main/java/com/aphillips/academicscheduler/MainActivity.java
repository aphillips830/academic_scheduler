package com.aphillips.academicscheduler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Hide the action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Startup screen animation
        Button title_button = findViewById(R.id.enter_button);
        TextView title_textview = findViewById(R.id.title_textview);
        Animation animate_title_button = AnimationUtils
                .loadAnimation(this, R.anim.animate_title_button);
        Animation animate_title = AnimationUtils
                .loadAnimation(this, R.anim.animate_title);
        title_button.setAnimation(animate_title_button);
        title_textview.setAnimation(animate_title);

        title_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Home.class);
                startActivity(intent);
            }
        });
    }
}