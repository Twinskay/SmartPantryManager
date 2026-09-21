package com.example.smartpantrymanager;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddIngredientActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_ingredient);
        Button btnSaveIngredient = findViewById(R.id.btnSaveIngredient);
        EditText txtIngredientName = findViewById(R.id.txtIngredientName);
        EditText txtQuantity = findViewById(R.id.txtQuantity);
        EditText txtUnit = findViewById(R.id.txtUnit);
        EditText txtExpiryDate = findViewById(R.id.txtExpiryDate);
        int ingredientId = getIntent().getIntExtra("ingredient_id",
                -1);
        if (ingredientId != -1) {
            txtIngredientName.setText(getIntent().getStringExtra("ingredient_name"));
            txtQuantity.setText(String.valueOf(
                    getIntent().getDoubleExtra("ingredient_quantity", 0)
            ));
            txtUnit.setText(getIntent().getStringExtra("ingredient_unit"));
            txtExpiryDate.setText(getIntent().getStringExtra("ingredient_expiry"));


        }
        btnSaveIngredient.setOnClickListener(v -> {

        String name = txtIngredientName.getText().toString().trim();
        String quantity = txtQuantity.getText().toString().trim();
        String unit = txtUnit.getText().toString().trim();
        String expiryDate = txtExpiryDate.getText().toString().trim();
        if (name.isEmpty() || quantity.isEmpty() || unit.isEmpty()) {
            Toast.makeText(AddIngredientActivity.this,
                    "Please fill in Ingredient Name, Quantity and Unit",
                    Toast.LENGTH_SHORT).show();
            return;
        }
            double quantityValue = Double.parseDouble(quantity);
            DatabaseHelper databaseHelper = new DatabaseHelper(AddIngredientActivity.this);
            boolean success;
            if (ingredientId != -1) {
                success = databaseHelper.updateIngredient(
                        ingredientId, name, quantityValue, unit, expiryDate
                );
            }
            else {
                success = databaseHelper.addIngredient(
                        name, quantityValue, unit, expiryDate
                );
            }
            if (success) {

                Toast.makeText(AddIngredientActivity.this,
                        "Ingredient saved",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddIngredientActivity.this,
                        "Could not save ingredient",
                        Toast.LENGTH_SHORT).show();
            }
        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        }
    }