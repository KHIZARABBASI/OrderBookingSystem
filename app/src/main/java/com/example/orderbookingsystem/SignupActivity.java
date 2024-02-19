package com.example.orderbookingsystem;

import static androidx.core.content.ContextCompat.startActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.orderbookingsystem.databinding.ActivitySignupBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.util.Objects;

public class SignupActivity extends AppCompatActivity {
    ActivitySignupBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySignupBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.registerInputName.getText().toString();
                String email = binding.registerInputEmail.getText().toString();
                String password = binding.registerInputPassword.getText().toString();
                
                createAccount(name, email, password);
            }
        });
        binding.gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class );
                startActivity(intent);
            }
        });
    }

    private void createAccount(String name, String email, String password) {
        if (name.equals("") || email.equals("") || password.equals("")){
            Toast.makeText(this, "Please fill all the field", Toast.LENGTH_SHORT).show();
        }
        else{
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Creating");
            progressDialog.setMessage("Account");
            progressDialog.show();

            mAuth.createUserWithEmailAndPassword(email.trim(), password.trim())
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(name).build();
                            Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).updateProfile(profileChangeRequest);
                            progressDialog.cancel();
                            Toast.makeText(SignupActivity.this, "Account Created", Toast.LENGTH_SHORT).show();
                            binding.registerInputName.setText("");
                            binding.registerInputEmail.setText("");
                            binding.registerInputPassword.setText("");

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.cancel();
                        }
                    });
        }
    }
}