package com.example.cs4084_mobile_application_development;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btn1 = (Button)findViewById(R.id.loginSubmit);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                finish();

            }
        });

        Button btn2 = (Button)findViewById(R.id.signupSubmit);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent k = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(k);

            }
        });
    }
}