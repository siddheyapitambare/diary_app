package com.example.diary_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    TextInputEditText username;
    TextInputEditText password;
    private Button loginButton;
    private TextView signupTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.user_name);
        password = findViewById(R.id.pass);
        loginButton = findViewById(R.id.login);
        signupTextView = findViewById(R.id.signup);

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateUsername() && validatePassword()) {
                    checkUser();
                    Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateUsername() {
        String val = username.getText().toString().trim();
        if (val.isEmpty()) {
            username.setError("Username cannot be empty");
            return false;
        } else {
            username.setError(null);
            return true;
        }
    }

    private boolean validatePassword() {
        String val = password.getText().toString().trim();
        if (val.isEmpty()) {
            password.setError("Password cannot be empty");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }

    private void checkUser() {
        String userUsername = username.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        String passwordFromDB = userSnapshot.child("password").getValue(String.class);

                        if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                            String uniqueKey = userUsername + userPassword;
                            Toast.makeText(MainActivity.this, uniqueKey, Toast.LENGTH_SHORT).show();

                            LoginDataSingleton.getInstance().setUniqueKey(uniqueKey);
                            LoginDataSingleton.getInstance().setUserUsername(userUsername);
                            LoginDataSingleton.getInstance().setUserPassword(userPassword);
                            LoginDataSingleton.getInstance().setNameFromDB(userSnapshot.child("fullname").getValue(String.class));
                            LoginDataSingleton.getInstance().setEmailFromDB(userSnapshot.child("email").getValue(String.class));
                            LoginDataSingleton.getInstance().setPhoneFromDB(userSnapshot.child("phone").getValue(String.class));

                            Intent intent_profile = new Intent(MainActivity.this, bottom_nav.class);
                            startActivity(intent_profile);
                            return;
                        }
                    }
                    password.setError("Invalid Credentials");
                    password.requestFocus();
                } else {
                    username.setError("User does not exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
