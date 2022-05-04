package com.projects.DataManagement;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

/*
This class is designed to provide access to the users database. It contains
various methods, including the CRUD methods or search tools.
 */
public class UserHelperDB extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "health_app.db"; //The database's name
    private static final String TABLE_RECORD = "tbl_users"; // user table name
    private static int DATABASE_VERSION = 2;


    private static final String COLUMN_ID = "Id"; //The id column name
    private static final String COLUMN_AGE = "Age"; //The age column name
    private static final String COLUMN_GENDER = "Gender"; //The gender column name
    private static final String COLUMN_HEIGHT = "Height"; //The height column name
    private static final String COLUMN_WEIGHT = "Weight"; //The weight column name
    private static final String COLUMN_SPORT_STATUS = "SportStatus"; //The sport status column name
    private static final String COLUMN_USERNAME= "Username"; //The username column name
    private static final String COLUMN_PASSWORD = "Password"; //The password column name

    String statement = "CREATE TABLE IF NOT EXISTS " + TABLE_RECORD + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_AGE + " INTEGER," +
            COLUMN_GENDER + " TEXT," +
            COLUMN_HEIGHT + " REAL," +
            COLUMN_WEIGHT + " REAL," +
            COLUMN_USERNAME + " TEXT," +
            COLUMN_PASSWORD + " TEXT," +
            COLUMN_SPORT_STATUS + " TEXT );";

    private Context context;

    /**
     * A standard constructor
     * @param context
     */
    public UserHelperDB(@Nullable Context context) {
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
     * An insertion method. allows to add users to the users table
     * @param record is the user to add
     * @return the user after being added to the users table.
     */
    public User addUser(User record)
    {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_AGE, record.getAge());
        values.put(COLUMN_GENDER, record.getGender().name());
        values.put(COLUMN_HEIGHT, record.getHeight());
        values.put(COLUMN_WEIGHT, record.getWeight());
        values.put(COLUMN_SPORT_STATUS, record.getStatus().name());
        values.put(COLUMN_USERNAME, record.getUsername());
        values.put(COLUMN_PASSWORD, record.getPassword());
        long id = database.insert(TABLE_RECORD, null, values);
        record.setId(id);
        database.close();
        return record;
    }

    /**
     * An deletion method. allows to remove users from the users table
     * @param id is user id to be deleted
     */
    public void deleteUser(long id) {
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_RECORD, COLUMN_ID + " = " + id, null);
        database.close();
    }

    /**
     * A method allowing to empty the users table completely.
     * That is, remove all the users from the table.
     */
    public void emptyAll(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_RECORD, null, null);
        database.close();
    }

    /**
     * A method for updating the age of a user.
     * @param replacement is the new age to be replaced with the previous one.
     * @param id is the user's id
     */
    public void updateUserAge(int replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_AGE, replacement);
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A method for updating the gender of a user.
     * @param replacement is the new gender to be replaced with the previous one.
     * @param id is the user's id
     */
    public void updateUserGender(User.Gender replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_GENDER, replacement.name());
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A method for updating the height of a user.
     * @param replacement is the new height to be replaced with the previous one.
     * @param id is the user's id
     */
    public void updateUserHeight(double replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_HEIGHT, replacement);
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A method for updating the weight of a user.
     * @param replacement is the new weight to be replaced with the previous one.
     * @param id is the user's id
     */
    public void updateUserWeight(double replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_WEIGHT, replacement);
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A method for updating the sport status of a user.
     * @param replacement is the new sport status to be replaced with the previous one.
     * @param id is the user's id
     */
    public void updateUserSportStatus(User.SportStatus replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_SPORT_STATUS, replacement.name());
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A method for updating the password of a user.
     * @param replacement is the new password to be replaced with the previous one.
     * @param id is the user's id
     */
    public void updatePassword(String replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PASSWORD, replacement);
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A method for updating all saved information about a user.
     * @param replacement is a user object, the new data will be taken from there
     * @param id is the user's id
     */
    public void updateUser(User replacement, long id){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_AGE, replacement.getAge());
        cv.put(COLUMN_GENDER, replacement.getGender().name());
        cv.put(COLUMN_HEIGHT, replacement.getHeight());
        cv.put(COLUMN_WEIGHT, replacement.getWeight());
        cv.put(COLUMN_SPORT_STATUS, replacement.getStatus().name());
        cv.put(COLUMN_PASSWORD, replacement.getPassword());
        String inField = COLUMN_ID + "=?";
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.update(TABLE_RECORD, cv, inField, new String[]{String.valueOf(id)});
        sqLiteDatabase.close();
    }

    /**
     * A search method, can be used to get a list of users with a certain property.
     * @param column is the filtering property
     * @param key is the value that has to be saved under a user for the user to be included in the returned list.
     * @param <T> is the java type of the 'key' parameter.
     * @return a list of user with the specified property
     */
    public <T extends Object> ArrayList<User> searchByKey(String column, T key){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        Cursor c = sqLiteDatabase.query(TABLE_RECORD
                , null,
                column + "=?", new String[]{String.valueOf(key)},
                null, null, null);
        ArrayList<User> arr = new ArrayList<>();
        c.moveToFirst();
        long id;
        int age;
        User.Gender gender;
        double weight, height;
        User.SportStatus status;
        String username, password;
        User user;
        while(!(c.isAfterLast())){
            id = c.getLong(c.getColumnIndex(COLUMN_ID));
            age = c.getInt(c.getColumnIndex(COLUMN_AGE));
            gender = User.Gender.valueOf(c.getString(c.getColumnIndex(COLUMN_GENDER)));
            weight = c.getDouble(c.getColumnIndex(COLUMN_WEIGHT));
            height = c.getDouble(c.getColumnIndex(COLUMN_HEIGHT));
            username = c.getString(c.getColumnIndex(COLUMN_USERNAME));
            password = c.getString(c.getColumnIndex(COLUMN_PASSWORD));
            status = User.SportStatus.valueOf(c.getString(c.getColumnIndex(COLUMN_SPORT_STATUS)));
            user = new User(username, password, age, gender, weight, height, status);
            user.setId(id);
            arr.add(user);
            c.moveToNext();
        }
        c.close();
        sqLiteDatabase.close();
        return arr;
    }

    public User containsUser(String username, String password){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TABLE_RECORD
                , null,
                COLUMN_USERNAME + "=?", new String[]{username},
                null, null, null);
        cursor.moveToNext();
        if(cursor.isAfterLast()) return null;
        User user = new User(
            username,
            cursor.getString(cursor.getColumnIndex(COLUMN_PASSWORD)),
            cursor.getInt(cursor.getColumnIndex(COLUMN_AGE)),
            User.Gender.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_GENDER))),
            cursor.getDouble(cursor.getColumnIndex(COLUMN_WEIGHT)),
            cursor.getDouble(cursor.getColumnIndex(COLUMN_HEIGHT)),
            User.SportStatus.valueOf(cursor.getString(cursor.getColumnIndex(COLUMN_SPORT_STATUS)))
        );
        cursor.close();
        database.close();
        return (user.getPassword().equals(password)) ? user : null;
    }

    public boolean containsUserName(String username){
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.query(TABLE_RECORD
                , null,
                COLUMN_USERNAME + "=?", new String[]{username},
                null, null, null);
        cursor.moveToNext();
        return !cursor.isAfterLast();
    }
}
