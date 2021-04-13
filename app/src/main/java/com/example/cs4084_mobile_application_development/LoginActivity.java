package com.example.cs4084_mobile_application_development;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        //This "Button" creates a button for signing in
        Button signInButton = (Button)findViewById(R.id.loginSubmit);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //This gets the email of the user
                TextInputEditText emailInput = findViewById(R.id.loginUsername);
                //This gets the password written down
                TextInputEditText passwordInput = findViewById(R.id.loginPassword);
                if(emailInput.getText().toString().trim().length() > 0 && passwordInput.getText().toString().trim().length() > 0) {
                    signIn(emailInput.getText().toString(), passwordInput.getText().toString());
                }
            }
        });
        //This button brings you to the Sign up page
        Button signUpButton = (Button)findViewById(R.id.signupSubmit);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });
    }

    /**
     * This function checks to see if the new email and password is valid or not
     * @param email     This checks the email written down in the email box to see if it's
     * @param password  this gets the password written down in the password box
     * @return indicating whether the sign in is successful by signing you in or giving a message failure
     */
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // if the sign in is a success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // if the sign up fails it will display this message
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }

}