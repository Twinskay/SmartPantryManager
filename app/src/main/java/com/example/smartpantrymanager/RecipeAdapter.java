package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import android.content.Intent;


    public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
        private ArrayList<Recipe> recipeList;
        public RecipeAdapter(ArrayList<Recipe> recipeList) {
            this.recipeList = recipeList;
        }
        public static class RecipeViewHolder extends RecyclerView.ViewHolder {
            TextView txtRecipeName;
            Button btnViewRecipe;


            public RecipeViewHolder(@NonNull View itemView) {
                super(itemView);

                txtRecipeName = itemView.findViewById(R.id.txtRecipeName);
                btnViewRecipe = itemView.findViewById(R.id.btnViewRecipe);
            }
        }
            @NonNull
            @Override
            public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_recipe, parent, false);

                return new RecipeViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
                Recipe recipe = recipeList.get(position);
                holder.txtRecipeName.setText(recipe.getName());
                holder.btnViewRecipe.setOnClickListener(v -> {
                    Intent intent = new Intent(v.getContext(), RecipeDetailActivity.class);

                    intent.putExtra("recipe_id", recipe.getId());
                    intent.putExtra("recipe_name", recipe.getName());
                    intent.putExtra("recipe_steps", recipe.getSteps());

                    v.getContext().startActivity(intent);
                });

            }

            @Override
            public int getItemCount() {
                return recipeList.size();

        }
    }

