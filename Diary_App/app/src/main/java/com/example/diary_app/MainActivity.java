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
    private TextView signupTextView; // Declare the TextView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = findViewById(R.id.user_name);
        password = findViewById(R.id.pass);
        loginButton = findViewById(R.id.login);
        signupTextView = findViewById(R.id.signup); // Initialize the TextView

        signupTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Intent to start the signup activity
                Intent intent = new Intent(MainActivity.this, signup.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Validate username and password before checking user credentials
                if (validateUsername() && validatePassword()) {
                    checkUser();
                    Toast.makeText(MainActivity.this, "Login", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Validate the entered username
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

    // Validate the entered password
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

    // Check user credentials in the Firebase database
    private void checkUser() {
        String userUsername = username.getText().toString().trim();
        String userPassword = password.getText().toString().trim();

        // Reference to the "User" node in the Firebase database
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        // Query to check if a user with the given username exists
        Query checkUserDatabase = reference.orderByChild("username").equalTo(userUsername);

        // Add a ValueEventListener to the query to check for the existence of the user
        checkUserDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User exists, check the entered password
                    String passwordFromDB = snapshot.child(userUsername).child("password").getValue(String.class);


                    if (passwordFromDB != null && passwordFromDB.equals(userPassword)) {
                        // Password is correct, retrieve user data and navigate to the home activity

                        // Create a unique key by concatenating userUsername and userPassword
                        String uniqueKey = userUsername + userPassword;
                        Toast.makeText(MainActivity.this, uniqueKey, Toast.LENGTH_SHORT).show();

                        // Set data in the LoginDataSingleton for later use
                        LoginDataSingleton.getInstance().setUniqueKey(uniqueKey);
                        LoginDataSingleton.getInstance().setUserUsername(userUsername);
                        LoginDataSingleton.getInstance().setUserPassword(userPassword);
                        LoginDataSingleton.getInstance().setNameFromDB(snapshot.child("fullname").getValue(String.class));
                        LoginDataSingleton.getInstance().setEmailFromDB(snapshot.child("email").getValue(String.class));
                        LoginDataSingleton.getInstance().setPhoneFromDB(snapshot.child("phone").getValue(String.class));

                        // Start the home activity
                        Intent intent_profile = new Intent(MainActivity.this, bottom_nav.class);
                        startActivity(intent_profile);
                    } else {
                        // Password is incorrect, display an error message
                        password.setError("Invalid Credentials");
                        password.requestFocus();
                    }
                } else {
                    // User does not exist, display an error message
                    username.setError("User does not exist");
                    username.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Handle database error
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
