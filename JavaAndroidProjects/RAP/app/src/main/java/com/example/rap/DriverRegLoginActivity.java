package com.example.rap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverRegLoginActivity extends AppCompatActivity {

    TextView accountCreate;
    Button signInDriver, signUpDriver;
    EditText emailET, passwordET;

    FirebaseAuth mAuth;
    DatabaseReference DriverDatabaseRef;
    String OnlineDriverID;

    ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_reg_login);

        accountCreate = (TextView)findViewById(R.id.accountCreate);
        signInDriver = (Button)findViewById(R.id.signInDriver);
        signUpDriver = (Button)findViewById(R.id.signUpDriver);
        emailET = (EditText)findViewById(R.id.driverEmail);
        passwordET = (EditText)findViewById(R.id.driverPassword);

        mAuth = FirebaseAuth.getInstance();

        loadingBar = new ProgressDialog(this);

        signUpDriver.setVisibility(View.INVISIBLE);
        signUpDriver.setEnabled(false);

        accountCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInDriver.setVisibility(View.INVISIBLE);
                accountCreate.setVisibility(View.INVISIBLE);
                signUpDriver.setVisibility(View.VISIBLE);
                signUpDriver.setEnabled(true);
            }
        });

        signUpDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                RegisterDriver(email, password);
            }
        });

        signInDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString();
                String password = passwordET.getText().toString();

                SignInDriver(email, password); 
            }
        });
    }

    private void SignInDriver(String email, String password) {
        loadingBar.setTitle("Вхід Водія");
        loadingBar.setMessage("Будь Ласка, дочекайтесь завантаження");
        loadingBar.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(DriverRegLoginActivity.this, "Вхід успішний", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                    Intent driverIntent = new Intent(DriverRegLoginActivity.this, DriversMapActivity.class);
                    startActivity(driverIntent);
                }
                else{
                    Toast.makeText(DriverRegLoginActivity.this, "Помилка входу, спробуйте ще раз", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }

    private void RegisterDriver(String email, String password) {
        loadingBar.setTitle("Реєстрація Водія");
        loadingBar.setMessage("Будь Ласка, дочекайтесь завантадеження");
        loadingBar.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    OnlineDriverID = mAuth.getCurrentUser().getUid();
                    DriverDatabaseRef = FirebaseDatabase.getInstance().getReference()
                            .child("Users").child("Customers").child(OnlineDriverID);
                    DriverDatabaseRef.setValue(true);


                    Intent driverIntent = new Intent(DriverRegLoginActivity.this, DriversMapActivity.class);
                    startActivity(driverIntent);


                    Toast.makeText(DriverRegLoginActivity.this, "Реєстрація пройшла успішно", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
                else {
                    Toast.makeText(DriverRegLoginActivity.this, "Помилка ", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }
}