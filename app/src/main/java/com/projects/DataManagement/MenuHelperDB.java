package com.projects.DataManagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class MenuHelperDB extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "health_app.db"; //The database's name
    private static final String TABLE_RECORD = "tbl_menu"; // user table name
    private static int DATABASE_VERSION = 2;

    private static final String COLUMN_ID = "Id"; //The id column's name
    private static final String COLUMN_CONSUMER = "Consumer"; //The consumer column's name
    private static final String COLUMN_NAME = "Name"; //The name column's name
    private static final String COLUMN_GRAMS = "Grams"; //The grams column's name
    private static final String COLUMN_PROTEINS = "Proteins"; //The proteins column's name
    private static final String COLUMN_CARBS = "Carbohydrates"; //The carbohydrates column's name
    private static final String COLUMN_FATS = "Fats"; //The fats column's name
    private static final String COLUMN_DAYS = "Days"; //The days column's name
    private static final String COLUMN_MEALS = "Meals"; //The meals column's name
    private static final String COLUMN_TYPES = "Types"; //The types column's name

    String statement = "CREATE TABLE IF NOT EXISTS " + TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_GRAMS + " REAL, " +
            COLUMN_PROTEINS + " REAL, " +
            COLUMN_CARBS + " REAL, " +
            COLUMN_FATS + " REAL, " +
            COLUMN_DAYS + " INTEGER, " +
            COLUMN_MEALS + " INTEGER, " +
            COLUMN_CONSUMER + " TEXT, " +
            COLUMN_TYPES + " TEXT);";

    private Context context;

    /**
     * A standard constructor
     * @param context
     */
    public MenuHelperDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    /**
     * This is a method that belongs to the interface of the super class.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(statement);
    }

    /**
     * This is a method that belongs to the interface of the super class.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECORD);
        onCreate(db);
    }


    /**
     * An insertion method. allows to add menu items to the menu table
     * @param record is the menu item to add
     * @return the menu item after being added to the menu table.
     */
    public FoodItem addFoodItem(FoodItem record, int day, int meal)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, record.getFoodName());
        values.put(COLUMN_GRAMS, record.getGrams());
        values.put(COLUMN_PROTEINS, record.getProteins());
        values.put(COLUMN_CARBS, record.getCarbohydrates());
        values.put(COLUMN_FATS, record.getFats());
        values.put(COLUMN_CONSUMER, record.getConsumer());
        values.put(COLUMN_DAYS, day);
        values.put(COLUMN_MEALS, meal);
        values.put(COLUMN_TYPES, record.getType().name());
        long id = database.insert(TABLE_RECORD, null, values);
        record.setId(id);
        database.close();
        return record;
    }

    /**
     * An deletion method. allows to remove menu items from the menu table
     * @param id is the id of the menu item to be deleted
     */
    public void deleteFoodItem(long id) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_RECORD, COLUMN_ID + " = " + id, null);
        database.close();
    }

    /**
     * A method allowing to empty the menu table completely.
     * That is, remove all the menu items from the table.
     */
    public void emptyAll(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_RECORD, null, null);
        database.close();
    }

    /**
     * A method for updating the grams of a menu item.
     * @param replacement is the new amount of grams to be replaced with the previous one.
     * @param id is the menu item's id
     */
    public void updateGrams(double replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_GRAMS, replacement);
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A method for updating the proteins of a menu item.
     * @param replacement is the new amount of proteins to be replaced with the previous one.
     * @param id is the menu item's id
     */
    public void updateProteins(double replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PROTEINS, replacement);
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A method for updating the carbohydrates of a menu item.
     * @param replacement is the new amount of carbohydrates to be replaced with the previous one.
     * @param id is the menu item's id
     */
    public void updateCarbohydrates(double replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_CARBS, replacement);
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A method for updating the fats of a menu item.
     * @param replacement is the new amount of fats to be replaced with the previous one.
     * @param id is the menu item's id
     */
    public void updateFats(double replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FATS, replacement);
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A search method. can be used to get the menu of a user.
     * @param consumer is the possessor of the returned menu
     * @return a list of food items that represent a user's menu
     */
    public ArrayList<FoodItem> searchConsumerMenu(String consumer){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.query(TABLE_RECORD
                , null,
                COLUMN_CONSUMER + "=?", new String[]{consumer},
                null, null, "Days, Meals");
        ArrayList<FoodItem> arr = new ArrayList<>();
        c.moveToFirst();
        long id;
        String name;
        double grams, proteins, carbs, fats;
        FoodItem foodItem;
        FoodType type;
        while(!(c.isAfterLast())){
            id = c.getLong(c.getColumnIndex(COLUMN_ID));
            name = c.getString(c.getColumnIndex(COLUMN_NAME));
            grams = c.getDouble(c.getColumnIndex(COLUMN_GRAMS));
            consumer = c.getString(c.getColumnIndex(COLUMN_CONSUMER));
            proteins = c.getDouble(c.getColumnIndex(COLUMN_PROTEINS));
            carbs = c.getDouble(c.getColumnIndex(COLUMN_CARBS));
            type = FoodType.valueOf(c.getString(c.getColumnIndex(COLUMN_TYPES)));
            fats = c.getDouble(c.getColumnIndex(COLUMN_FATS));
            foodItem = new FoodItem(name, consumer, type, grams, proteins, carbs, fats);
            foodItem.setId(id);
            arr.add(foodItem);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();
        return arr;
    }

    /**
     * A search method. can be used to get the meal of a user at a certain time on a certain day.
     * @param consumer of the returned meal
     * @param day is the day on which the meal is consumed.
     *            it is an int ranges from 0 to 6 (0 = Sunday, 1 = Monday...)
     * @param meal is the index of the meal to be consumed.
     *             it is an int ranges from 0 to 2 (0 = Breakfast, 1 = Lunch...)
     * @return a list of food items representing a user's meal
     */
    public ArrayList<FoodItem> searchConsumerMeal(String consumer, int day, int meal){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.query(TABLE_RECORD
                , null,
                COLUMN_CONSUMER + "=? AND " +
                        COLUMN_DAYS + "=? AND " +
                        COLUMN_MEALS + "=?;"
                , new String[]{consumer, String.valueOf(day), String.valueOf(meal)},
                null, null, null);
        ArrayList<FoodItem> arr = new ArrayList<>();
        c.moveToFirst();
        long id;
        String name;
        double grams, proteins, carbs, fats;
        FoodItem foodItem;
        FoodType type;
        while(!(c.isAfterLast())){
            id = c.getLong(c.getColumnIndex(COLUMN_ID));
            name = c.getString(c.getColumnIndex(COLUMN_NAME));
            grams = c.getDouble(c.getColumnIndex(COLUMN_GRAMS));
            consumer = c.getString(c.getColumnIndex(COLUMN_CONSUMER));
            proteins = c.getDouble(c.getColumnIndex(COLUMN_PROTEINS));
            carbs = c.getDouble(c.getColumnIndex(COLUMN_CARBS));
            fats = c.getDouble(c.getColumnIndex(COLUMN_FATS));
            type = FoodType.valueOf(c.getString(c.getColumnIndex(COLUMN_TYPES)));
            foodItem = new FoodItem(name, consumer, type, grams, proteins, carbs, fats);
            foodItem.setId(id);
            arr.add(foodItem);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();
        return arr;
    }

    public void deleteConsumerMenu(String consumer) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_RECORD, COLUMN_CONSUMER + " = '" + consumer + "'", null);
        database.close();
    }
}
