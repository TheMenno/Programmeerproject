package com.example.menno_000.programmeerproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import org.w3c.dom.EntityReference;

import java.util.Date;
import java.util.Map;

import static java.sql.Types.INTEGER;
import static java.text.Collator.PRIMARY;
import static android.provider.Contacts.SettingsColumns.KEY;

public class StoredFoodDatabase extends SQLiteOpenHelper {

    private static final String NAME = "food.db";
    private static final int VERSION = 1;
    private static final String TABLE = "Food";
    private static StoredFoodDatabase instance = null;

    private static final String ID = "_id";
    private static final String API_ID = "api_id";
    private static final String PRODUCT_NAME = "product";
    private static final String CALORIES = "calories";
    private static final String AMOUNT = "amount";
    private static final String DATE = "date";
    private static final String MEAL = "meal";

    private StoredFoodDatabase(Context context) {
        super(context, TABLE, null, VERSION);
    }

    // Makes sure only one instance of the database exists
    public static StoredFoodDatabase getInstance(Context context) {
        if (instance != null) {
            return instance;
        } else {
            instance = new StoredFoodDatabase(context);
            return instance;
        }
    }


    // Creates the database table
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String sql = "CREATE TABLE " + TABLE + "(" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                API_ID + " INTEGER NOT NULL, " +
                PRODUCT_NAME + " TEXT NOT NULL, " +
                CALORIES + " INTEGER NOT NULL, " +
                AMOUNT + " INTEGER NOT NULL, " +
                DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP, "  +
                MEAL + " STRING NOT NULL);";

        sqLiteDatabase.execSQL(sql);
    }


    // Reset the database
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(sqLiteDatabase);
    }


    // Add a new instance
    public void insert(Food food){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(API_ID, food.getApi_id());
        values.put(PRODUCT_NAME, food.getProduct_name());
        values.put(CALORIES, food.getCalories());
        values.put(AMOUNT, food.getAmount());
        values.put(MEAL, food.getMeal());

        sqLiteDatabase.insert(TABLE,null,values);
        sqLiteDatabase.close();
    }


    // Retrieve an instance
    public Cursor selectAll() {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE,null);
        return cursor;
    }


    // Retrieve an instance
    public Cursor selectMeal(String meal) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE + " WHERE " + MEAL + " = '" +
                meal + "'",null);
        return cursor;
    }


    // Delete an instance
    public void delete(long id) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE,ID + "=" + id,null);
        sqLiteDatabase.close();
    }
}