package com.example.samsmile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText mEmailEt, mPasswordEt;
    Button loginBtn, signUpbtn;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        firebaseAuth = FirebaseAuth.getInstance();
        mEmailEt = findViewById(R.id.emailEt);
        mPasswordEt = findViewById(R.id.passwordEt);
        loginBtn = findViewById(R.id.login_btn);
        signUpbtn = findViewById(R.id.register_btn);
        pd = new ProgressDialog(this);
        pd.setMessage("Logging in....");
        setOnClickListeners();
    }

    private void setOnClickListeners() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mEmailEt.getText().toString().trim();
                String password = mPasswordEt.getText().toString().trim();

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    mEmailEt.setError("Invalid Email");
                    mEmailEt.setFocusable(true);
                }
                else{
                 loginUser(email, password);   
                }
            }
        });
        signUpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this, RegisterActivity.class));
            }
        });
    }

    private void loginUser(String email, String password) {
        pd.show();
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            pd.dismiss();
                            Toast.makeText(loginActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            startActivity(new Intent(loginActivity.this, homePageActivity.class));
                        } else {
                            pd.dismiss();
                            // If sign in fails, display a message to the user.
                            Toast.makeText(loginActivity.this, "Authentication Failed.", Toast.LENGTH_SHORT).show();

                        }
                    }}).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(loginActivity.this, "Log In failed.. Please check your internet connection", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void checkUserSatus(){
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            startActivity(new Intent(loginActivity.this, homePageActivity.class));
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}