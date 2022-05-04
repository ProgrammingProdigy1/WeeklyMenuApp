package com.projects.DataManagement;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.projects.Screen1.R;
import com.projects.Screen2.FoodRecyclerItem;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private ArrayList<FoodRecyclerItem> menu;
    private Context context;

    public static class MenuViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView name;
        public Spinner cs;

        public MenuViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            name = itemView.findViewById(R.id.foodItemName_tv);
            cs = itemView.findViewById(R.id.CaloriesScale_sp);
        }
    }

    public MenuAdapter(ArrayList<FoodRecyclerItem> menu, Context context){
        this.menu = new ArrayList<>(menu);
        this.context = context;
    }
    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_item, parent, false);
        return new MenuViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MenuViewHolder holder, int position) {
        FoodRecyclerItem current = menu.get(position);
        holder.imageView.setImageResource(current.getImageResource());
        holder.name.setText(current.getFoodItem().getFoodName());
        String[] scales = {"כלי מדידה","כוס", "ספל", "צנצנת", "כף", "כפית"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.context, R.layout.spinner_item, scales);
        holder.cs.setAdapter(adapter);
    }

    @Override
    public int getItemCount() { return menu.size(); }

    public void filterList(ArrayList<FoodRecyclerItem> arrayList){
        menu = new ArrayList<>(arrayList);
        notifyDataSetChanged();
    }
}
