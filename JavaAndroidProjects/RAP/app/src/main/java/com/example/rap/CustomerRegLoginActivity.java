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

public class CustomerRegLoginActivity extends AppCompatActivity {

    TextView accountCreateCustomer;
    Button signInCustomer, signUpCustomer;
    EditText emailETcust, passwordETcust;

    FirebaseAuth mAuth;
    DatabaseReference CustomerDatabaseRef;
    String OnlineCustomerID;


    ProgressDialog loadingBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_reg_login);

        accountCreateCustomer = (TextView)findViewById(R.id.accountCreateCustomer);
        signUpCustomer = (Button)findViewById(R.id.signUpCustomer);
        signInCustomer = (Button)findViewById(R.id.signInCustomer);
        emailETcust = (EditText)findViewById(R.id.customerEmail);
        passwordETcust = (EditText)findViewById(R.id.customerPassword);

        mAuth = FirebaseAuth.getInstance();


        loadingBar = new ProgressDialog(this);

        accountCreateCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signInCustomer.setVisibility(View.INVISIBLE);
                accountCreateCustomer.setVisibility(View.INVISIBLE);
                signUpCustomer.setVisibility(View.VISIBLE);
                signUpCustomer.setEnabled(true);
            }
        });

        signUpCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailETcust.getText().toString();
                String password = passwordETcust.getText().toString();

                RegisterCustomer(email, password);
            }
        });

        signInCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailETcust.getText().toString();
                String password = passwordETcust.getText().toString();

                SignInCustomer(email, password);
            }
        });
    }

    private void SignInCustomer(String email, String password) {
        loadingBar.setTitle("Вхід Користувача");
        loadingBar.setMessage("Будь ласка, дочекайтесь завершення");
        loadingBar.show();

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CustomerRegLoginActivity.this, "Вхід успішний", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                    Intent customerTntent = new Intent(CustomerRegLoginActivity.this, CustomerMap.class);
                    startActivity(customerTntent);
                }
                else{
                    Toast.makeText(CustomerRegLoginActivity.this, "Помилка, спробуйте ще раз", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }

            }
        });
    }

    private void RegisterCustomer(String email, String password) {
        loadingBar.setTitle("Реєстрація користувача");
        loadingBar.setMessage("Будь ласка, дочекайтесь завантаження");
        loadingBar.show();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    OnlineCustomerID = mAuth.getCurrentUser().getUid();
                    CustomerDatabaseRef = FirebaseDatabase.getInstance().getReference()
                            .child("Users").child("Customers").child(OnlineCustomerID);
                    CustomerDatabaseRef.setValue(true);


                    Intent customerTntent = new Intent(CustomerRegLoginActivity.this, CustomerMap.class);
                    startActivity(customerTntent);


                    Toast.makeText(CustomerRegLoginActivity.this, "Реєстрація пройшла успішно", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();

                }
                else{
                    Toast.makeText(CustomerRegLoginActivity.this, "Помилка", Toast.LENGTH_SHORT).show();
                    loadingBar.dismiss();
                }
            }
        });
    }
}
