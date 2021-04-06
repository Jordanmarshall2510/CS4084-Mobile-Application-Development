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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        Button signUpButton = (Button)findViewById(R.id.signupSubmit);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                TextInputEditText emailInput = findViewById(R.id.etemail);
                TextInputEditText passwordInput = findViewById(R.id.etPassword);
                if(emailInput.getText().toString()!= null && passwordInput.getText().toString() != null) {
                    createAccount(emailInput.getText().toString(), passwordInput.getText().toString());
                }
            }

        });

        Button goBackButton = (Button)findViewById(R.id.goBack);
        goBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }


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

    private void updateUI(FirebaseUser user)
    {
        if(user != null) {
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }
    }

}