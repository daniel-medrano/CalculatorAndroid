package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {
    Button btnStart;
    SharedPreferences sharedPreferences;
    boolean agreed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btnStart = findViewById(R.id.btnStart);
        sharedPreferences = getSharedPreferences("terms and conditions", this.MODE_PRIVATE);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                agreed = sharedPreferences.getBoolean("agreed", false);
                Intent intent = new Intent(WelcomeActivity.this, !agreed ? TermsAndConditionsActivity.class : MainActivity.class);
                startActivity(intent);
            }
        });
    }
}