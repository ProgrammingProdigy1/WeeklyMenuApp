package com.projects.DataManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.Screen1.R;
import com.projects.Screen2.FoodRecyclerItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MenuLoadAdapter extends RecyclerView.Adapter<MenuLoadAdapter.MenuViewHolder> {

    private ArrayList<FoodRecyclerItem> menu;
    private Context context;

    public static class MenuViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView name, grams;

        public MenuViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.meal_image_view);
            name = itemView.findViewById(R.id.mealItemName_tv);
            grams = itemView.findViewById(R.id.grams_tv);
        }
    }

    public MenuLoadAdapter(ArrayList<FoodRecyclerItem> menu, Context context){
        this.menu = new ArrayList<>(menu);
        this.context = context;
    }

    @NonNull
    @Override
    public MenuLoadAdapter.MenuViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_meal_item, parent, false);
        return new MenuLoadAdapter.MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MenuLoadAdapter.MenuViewHolder holder, int position) {
        FoodRecyclerItem current = menu.get(position);
        holder.imageView.setImageResource(current.getImageResource());
        holder.name.setText(current.getFoodItem().getFoodName());
        holder.grams.setText("" + current.getFoodItem().getGrams());
    }

    @Override
    public int getItemCount() { return menu.size(); }
}
