package com.example.samsmile;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    //Initialize views
    Button mRegisterBtn, mLoginBtn;
    FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRegisterBtn = findViewById(R.id.register_btn);
        mLoginBtn = findViewById(R.id.login_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        // set on click listeners
        setOnClickListeners();

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Welcome");
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#2466e0")));

    }


    // Handles button click events
    private void setOnClickListeners() {
        //Start Register Activity Layout
        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, loginActivity.class));
            }
        });
    }
    private void checkUserSatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(MainActivity.this, homePageActivity.class));
            finish();
        }
        else{
        }
    }

    @Override
    protected void onStart() {
        checkUserSatus();
        super.onStart();
    }
}