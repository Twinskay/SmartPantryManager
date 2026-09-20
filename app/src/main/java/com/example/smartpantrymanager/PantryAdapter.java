package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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

        public PantryViewHolder(View itemView) {
            super(itemView);
            txtItemName = itemView.findViewById(R.id.txtItemName);
            txtItemQuantity = itemView.findViewById(R.id.txtItemQuantity);
            txtItemExpiry = itemView.findViewById(R.id.txtItemExpiry);

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

    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }
}


