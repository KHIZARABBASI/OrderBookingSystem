package com.example.orderbookingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView gotoSignUp;
    Button signInBtn;
    EditText emailInput, passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        gotoSignUp=findViewById(R.id.goto_signup);
        signInBtn=findViewById(R.id.siginBtn);
        emailInput = findViewById(R.id.signInInputEmail);
        passwordInput = findViewById(R.id.signInInputPassword);

        gotoSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });

        signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailInput.getText().toString();
                String password = passwordInput.getText().toString();

                signInAccount(email, password);

            }
        });
    }

    private void signInAccount(String email, String password) {
        if (email.equals("") || password.equals("")){
            Toast.makeText(this, "Please fill all the field", Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("SignIn");
            progressDialog.setMessage("Account");
            progressDialog.show();

            mAuth.signInWithEmailAndPassword(email.trim(), password.trim())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                        }
                    })
        }
    }
}