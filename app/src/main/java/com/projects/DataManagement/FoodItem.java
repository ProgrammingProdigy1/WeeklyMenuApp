package com.projects.DataManagement;

public class FoodItem {

    private String foodName, consumer;
    private FoodType type;
    private long id;
    private double grams, proteins, carbohydrates, fats;

    public FoodItem(String foodName, String consumer, FoodType type, CaloriesScale cs, double scale, double proteins, double carbohydrates, double fats){
        this.foodName = (foodName.length() < 11) ? foodName : "";
        this.type = type;
        this.consumer = consumer;
        // Example: scale = 2, cs = GLASS -> two glasses -> 2 * 240 = 480 grams
        this.grams = cs.totalGrams() * scale;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
    }

    public FoodItem(String foodName, String consumer, CaloriesScale cs, double scale, double proteins, double carbohydrates, double fats){
        this.foodName = foodName;
        this.consumer = consumer;
        // Example: scale = 2, cs = GLASS -> two glasses -> 2 * 240 = 480 grams
        this.grams = cs.totalGrams() * scale;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
    }

    public FoodItem(String foodName, String consumer, FoodType type, double grams, double proteins, double carbohydrates, double fats){
        this.foodName = foodName;
        this.type = type;
        this.consumer = consumer;
        this.grams = grams;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
    }

    public FoodItem(FoodItem foodItem){
        this.foodName = foodItem.foodName;
        this.type = foodItem.type;
        this.consumer = foodItem.consumer;
        this.grams = foodItem.grams;
        this.proteins = foodItem.proteins;
        this.carbohydrates = foodItem.carbohydrates;
        this.fats = foodItem.fats;
    }

    public FoodItem(String foodName, String consumer, double grams, double proteins, double carbohydrates, double fats){
        this.foodName = foodName;
        this.consumer = consumer;
        this.grams = grams;
        this.proteins = proteins;
        this.carbohydrates = carbohydrates;
        this.fats = fats;
    }

    public String getFoodName() {
        return foodName;
    }

    public double getGrams(){
        return this.grams;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public double getFats() {
        return fats;
    }

    public double getProteins() {
        return proteins;
    }

    public long getId() {
        return id;
    }

    public FoodType getType() { return type; }

    public void setType(FoodType type) { this.type = type; }

    public void setId(long id) {
        this.id = id;
    }

    public String getConsumer() { return consumer; }

    public void setGrams(double grams){
        this.carbohydrates /= this.grams;
        this.carbohydrates *= grams;
        this.proteins /= this.grams;
        this.proteins *= grams;
        this.fats /= this.grams;
        this.fats *= grams;
        this.grams = grams;
    }

    public void setGrams(CaloriesScale cs, double scale){
        setGrams(cs.totalGrams() * scale);
    }

    public void setConsumer(String consumer) { this.consumer = consumer; }

    // Allows the user to upload data concerning the food he has eaten by offering constant, standard calories scales
    public enum CaloriesScale {

        כוס, ספל, צנצנת, כף, כפית;

        // The calculation here was simple: I searched on Google: "how many ml (milliliters) is a cup/glass/jar...",
        // and converted the result to grams (1 ml = 1 gram)
        public double totalGrams(){
            switch (this){
                case כוס:
                    return 240;
                case ספל:
                    return 236.588237;
                case צנצנת:
                    return 500;
                case כפית:
                    return 4.92892159;
            }
            return 14.7867648; // SPOON
        }
    }
}
