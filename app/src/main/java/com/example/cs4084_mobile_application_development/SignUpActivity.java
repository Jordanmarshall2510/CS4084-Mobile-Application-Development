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
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
/**
 * This function deals with the two buttons on the sign up page that allow
 * you to sign up and return to the login page.
 */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        //This button is used to attempt to sign up
        Button signUpButton = (Button)findViewById(R.id.signupSubmit);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * this function gets the email and password written in the email and password boxes
             */
            public void onClick(View v)
            {
                //This gets the email of the user
                TextInputEditText emailInput = findViewById(R.id.etemail);
                //This gets the password written down
                TextInputEditText passwordInput = findViewById(R.id.etPassword);
                if(emailInput.getText().toString().trim().length() > 0 && passwordInput.getText().toString().trim().length() > 0 ){
                    createAccount(emailInput.getText().toString(), passwordInput.getText().toString());
                }
            }

        });

        //This button brings you back to Login page
        Button goBackButton = (Button)findViewById(R.id.goBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * This function is used to move from the sign up activity to the login activity
             */
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * This function checks to see if the email and password submitted is in the database
     * @param email     This gets the email written down in the email box
     * @param password  this gets the password written down in the password box
     * @return whether the sign up is successful by creating a new account or a displaying a failure message
     */
    private void createAccount(String email, String password) {
        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
        // [END create_user_with_email]
    }

    /**
     * This function connects the sign up activity to the main activity
     */
    private void updateUI(FirebaseUser user)
    {
        if(user != null) {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }
    }

}