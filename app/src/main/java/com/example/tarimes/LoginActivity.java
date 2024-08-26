package com.example.tarimes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tarimes.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private ActivityLoginBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.goToRegisterActTv.setOnClickListener(v -> startActivity(new Intent(LoginActivity.this,
                RegisterActivity.class)));

        binding.logBtn.setOnClickListener(v -> {
            String email = binding.emailET.getText().toString();
            String password = binding.passwordET.getText().toString();
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Поля не могут быть пустыми", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                finish(); // Prevent returning to LoginActivity
                            } else {
                                Toast.makeText(getApplicationContext(), "Ошибка входа: " + task.getException().getMessage(),
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
