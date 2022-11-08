package com.example.rap;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsActivity extends AppCompatActivity {

    private String getType;

    private CircleImageView circleImageView;
    private EditText nameET, phoneET, carET;
    private ImageView closeBtn, saveBtn;
    private TextView imageChangeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getType = getIntent().getStringExtra("type");


        circleImageView = (CircleImageView)findViewById(R.id.profile_image);
        nameET = (EditText)findViewById(R.id.name);
        phoneET = (EditText)findViewById(R.id.phone);

        carET = (EditText)findViewById(R.id.car_name);
        if(getType.equals("Drivers")){
            carET.setVisibility(View.VISIBLE);
        }


        closeBtn = (ImageView)findViewById(R.id.close_button);
        saveBtn = (ImageView)findViewById(R.id.save_button);
        imageChangeButton = (TextView)findViewById(R.id.change_photo_btn);


        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getType.equals("Drivers")){
                    startActivity(new Intent(SettingsActivity.this, DriversMapActivity.class));
                }else{
                    startActivity(new Intent(SettingsActivity.this, CustomerMap.class));

                }
            }
        });


    }
}