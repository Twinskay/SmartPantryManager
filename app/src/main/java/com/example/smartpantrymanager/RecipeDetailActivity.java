package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.TextView;
import android.content.Intent;
public class RecipeDetailActivity extends AppCompatActivity {
    TextView txtRecipeDetailName;
    TextView txtRecipeIngredients;
    TextView txtRecipeSteps;
    DatabaseHelper databaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);
        txtRecipeDetailName = findViewById(R.id.txtRecipeDetailName);
        txtRecipeIngredients = findViewById(R.id.txtRecipeIngredients);
        txtRecipeSteps = findViewById(R.id.txtRecipeSteps);
        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        int recipeId = intent.getIntExtra("recipe_id", -1);
        String recipeName = intent.getStringExtra("recipe_name");
        String recipeSteps = intent.getStringExtra("recipe_steps");
        txtRecipeDetailName.setText(recipeName);
        txtRecipeSteps.setText(recipeSteps);
        String recipeIngredients = databaseHelper.getRecipeIngredients(recipeId);
        txtRecipeIngredients.setText(recipeIngredients);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}