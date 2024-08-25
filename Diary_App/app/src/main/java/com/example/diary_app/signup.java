package com.example.diary_app;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class signup extends AppCompatActivity {


    TextInputEditText fullname;
    TextInputEditText email;
    TextInputEditText phone;
    TextInputEditText username;
    TextInputEditText password;
    private Button signupButton;
    private TextView loginTextView;
    FirebaseDatabase database;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        fullname=findViewById(R.id.signup_full_name);
        email=findViewById(R.id.signup_email);
        phone=findViewById(R.id.signup_phone);
        username=findViewById(R.id.signup_username);
        password=findViewById(R.id.signup_password);
        loginTextView = findViewById(R.id.login_redirect);
        signupButton=findViewById(R.id.signup_button);

        database=FirebaseDatabase.getInstance();
        reference=database.getReference("User");


        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String fullName =fullname.getText().toString().trim();
               String emailsignup=email.getText().toString().trim();
               String phonesignup=phone.getText().toString().trim();
               String usernamesignup=username.getText().toString().trim();
               String passwordsignup=password.getText().toString().trim();


                // Check for empty fields
                if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(emailsignup) || TextUtils.isEmpty(phonesignup)
                        || TextUtils.isEmpty(usernamesignup) || TextUtils.isEmpty(passwordsignup)) {
                    Toast.makeText(signup.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform Firebase operation
                logindataholder helperClass = new logindataholder(fullName, emailsignup, phonesignup, usernamesignup, passwordsignup);
                reference.child(fullName).setValue(helperClass);

                Toast.makeText(signup.this, "You have signed up successfully!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(signup.this, bottom_nav.class);
                startActivity(intent);



                }
        });

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle the login text view click
                Intent intent = new Intent(signup.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

}
