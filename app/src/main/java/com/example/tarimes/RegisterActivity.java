package com.example.tarimes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tarimes.databinding.ActivityRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.regBtn.setOnClickListener(v -> {
            String email = binding.emailET.getText().toString();
            String password = binding.passwordET.getText().toString();
            String username = binding.usernameET.getText().toString();
            if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Поля не могут быть пустыми", Toast.LENGTH_SHORT).show();
            } else {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                HashMap<String, String> userInfo = new HashMap<>();
                                userInfo.put("email", email);
                                userInfo.put("username", username);
                                FirebaseDatabase.getInstance().getReference().child("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(userInfo);
                                startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                                finish(); // Prevent returning to RegisterActivity
                            } else {
                                Toast.makeText(getApplicationContext(), "Ошибка регистрации: "
                                        + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });
    }
}
