package com.projects.Screen2;

import com.projects.DataManagement.FoodItem;
import com.projects.DataManagement.FoodType;
import com.projects.Screen1.R;

public class FoodRecyclerItem {
    private int imageResource;
    private FoodItem foodItem;
    public FoodRecyclerItem(FoodType foodType, FoodItem item) {
        switch (foodType){
            case DAIRY:
                this.imageResource = R.drawable.ic_dairy;
                break;
            case GRAIN:
                this.imageResource = R.drawable.ic_grain;
                break;
            case JUNK:
                this.imageResource = R.drawable.ic_junk_food;
                break;
            case FRUIT:
                this.imageResource = R.drawable.ic_fruit;
                break;
            case DESSERT:
                this.imageResource = R.drawable.ic_dessert;
                break;
            default:
                this.imageResource = R.drawable.ic_vegetable;
        }
        this.foodItem = new FoodItem(item);
    }

    public FoodItem getFoodItem() {
        return new FoodItem(foodItem);
    }

    public int getImageResource() { return imageResource; }
}
