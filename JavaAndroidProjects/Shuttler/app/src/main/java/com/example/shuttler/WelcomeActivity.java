package com.example.shuttler;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    Button driverbtn, userbtn, faqbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        driverbtn = (Button)findViewById(R.id.driverbtn);
        userbtn = (Button)findViewById(R.id.userbtn);
        faqbtn = (Button)findViewById(R.id.faqbtn);

        driverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent driverIntent = new Intent(WelcomeActivity.this, DriverRegLogActivity.class);
                startActivity(driverIntent);
            }
        });

        userbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent userIntent = new Intent(WelcomeActivity.this, UserRegLogActivity.class);
                startActivity(userIntent);
            }
        });

        faqbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent faqIntent = new Intent(WelcomeActivity.this, FaqActivity.class);
                startActivity(faqIntent);
            }
        });
    }
}