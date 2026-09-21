package com.example.smartpantrymanager;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {
    private ArrayList<PantryItem> ingredientList;

    public PantryAdapter(ArrayList<PantryItem> ingredientList) {
        this.ingredientList = ingredientList;
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {
        TextView txtItemName;
        TextView txtItemQuantity;
        TextView txtItemExpiry;
        Button btnEdit;
        Button btnDelete;

        public PantryViewHolder(View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemQuantity = itemView.findViewById(R.id.txtItemQuantity);
            txtItemExpiry = itemView.findViewById(R.id.txtItemExpiry);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }

    @Override
    public PantryAdapter.PantryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);
        return new PantryAdapter.PantryViewHolder(view);

    }

    @Override
    public void onBindViewHolder(PantryAdapter.PantryViewHolder holder, int position) {
        PantryItem item = ingredientList.get(position);
        holder.txtItemName.setText(item.getName());
        holder.txtItemQuantity.setText(item.getQuantity() + " " + item.getUnit());
        holder.txtItemExpiry.setText("Expiry: " + item.getExpiryDate());
            holder.btnEdit.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), AddIngredientActivity.class);
                intent.putExtra("ingredient_id", item.getId());
                intent.putExtra("ingredient_name", item.getName());
                intent.putExtra("ingredient_quantity", item.getQuantity());
                intent.putExtra("ingredient_unit", item.getUnit());
                intent.putExtra("ingredient_expiry", item.getExpiryDate());

                v.getContext().startActivity(intent);

            });
        holder.btnDelete.setOnClickListener(v -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(v.getContext());
            boolean deleted = databaseHelper.deleteIngredient(item.getId());
            if (deleted) {
                ingredientList.remove(position);
                notifyItemRemoved(position);

                Toast.makeText(v.getContext(),
                        "Ingredient deleted",
                        Toast.LENGTH_SHORT).show();
            }

        });

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
}


