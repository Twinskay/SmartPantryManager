package com.example.smartpantrymanager;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class SuggestedRecipesActivity extends AppCompatActivity {
    RecyclerView recyclerSuggestedRecipes;

    TextView txtNoRecipes;

    DatabaseHelper databaseHelper;
    RecipeAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_suggested_recipes);
        recyclerSuggestedRecipes = findViewById(R.id.recyclerSuggestedRecipes);
        txtNoRecipes = findViewById(R.id.txtNoRecipes);
        recyclerSuggestedRecipes.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper = new DatabaseHelper(this);
        ArrayList<Recipe> matchingRecipes = databaseHelper.getMatchingRecipes();
        recipeAdapter = new RecipeAdapter(matchingRecipes);
        recyclerSuggestedRecipes.setAdapter(recipeAdapter);
        if (matchingRecipes.isEmpty()) {
            txtNoRecipes.setVisibility(View.VISIBLE);
            recyclerSuggestedRecipes.setVisibility(View.GONE);
        } else {
            txtNoRecipes.setVisibility(View.GONE);
            recyclerSuggestedRecipes.setVisibility(View.VISIBLE);
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}