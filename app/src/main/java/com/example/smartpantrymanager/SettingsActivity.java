package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.SharedPreferences;

public class SettingsActivity extends AppCompatActivity {
    EditText txtProfileName;
    Button btnSaveProfile;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        txtProfileName = findViewById(R.id.txtProfileName);
        btnSaveProfile = findViewById(R.id.btnSaveProfile);
        btnBack = findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> {
            finish();
        });
        btnSaveProfile.setOnClickListener(v -> {
            String name = txtProfileName.getText().toString().trim();

            if (name.isEmpty()) {
                Toast.makeText(SettingsActivity.this,
                        "Please enter your name",
                        Toast.LENGTH_SHORT).show();
                return;
            }

            Toast.makeText(SettingsActivity.this,
                    "Profile saved",
                    Toast.LENGTH_SHORT).show();
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}