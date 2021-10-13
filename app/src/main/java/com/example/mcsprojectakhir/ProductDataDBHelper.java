package com.example.mcsprojectakhir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ProductDataDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "ProductDataDB.db";
    public static final Integer DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tblproductdata";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_NAME = "pd_name";
    public static final String TABLE_COLUMN_MIN_PLAYER = "pd_min";
    public static final String TABLE_COLUMN_MAX_PLAYER = "pd_max";
    public static final String TABLE_COLUMN_PRICE = "pd_price";
    public static final String TABLE_COLUMN_CREATED_AT = "pd_created";
    public static final String TABLE_COLUMN_LONGITUDE = "pd_longitude";
    public static final String TABLE_COLUMN_LATITUDE = "pd_latitude";


    List<Product> PD = new ArrayList<>();

    public ProductDataDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertPD(Product new_PD) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TABLE_COLUMN_NAME, new_PD.getProductName());
        cv.put(TABLE_COLUMN_MIN_PLAYER, new_PD.getProductMinPlayer());
        cv.put(TABLE_COLUMN_MAX_PLAYER, new_PD.getProductMaxPlayer());
        cv.put(TABLE_COLUMN_PRICE, new_PD.getProductPrice());
        cv.put(TABLE_COLUMN_CREATED_AT, new_PD.getCreated_at());
        cv.put(TABLE_COLUMN_LONGITUDE, new_PD.getLongitude());
        cv.put(TABLE_COLUMN_LATITUDE, new_PD.getLatitude());

        db.insert(TABLE_NAME, null, cv);


    }


    Cursor readAllProductData () {
        SQLiteDatabase db = this.getReadableDatabase();
        String PD_query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = null;

        if (db == null){

        }

        if (db != null) {
            cursor = db.rawQuery(PD_query, null);
        }

        return cursor;
    }


    public boolean updatePD (Product update_PD) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TABLE_COLUMN_ID, update_PD.getProductId());
        cv.put(TABLE_COLUMN_NAME, update_PD.getProductName());
        cv.put(TABLE_COLUMN_MIN_PLAYER, update_PD.getProductMinPlayer());
        cv.put(TABLE_COLUMN_MAX_PLAYER, update_PD.getProductMaxPlayer());
        cv.put(TABLE_COLUMN_PRICE, update_PD.getProductPrice());
        cv.put(TABLE_COLUMN_CREATED_AT, update_PD.getCreated_at());
        cv.put(TABLE_COLUMN_LONGITUDE, update_PD.getLongitude());
        cv.put(TABLE_COLUMN_LATITUDE, update_PD.getLatitude());


        db.update(TABLE_NAME, cv, "id = ?", new String [] { update_PD.getProductId() } );

        return true;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table_query = "CREATE TABLE " + TABLE_NAME + " (" + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_COLUMN_NAME + " TEXT NOT NULL, " + TABLE_COLUMN_MIN_PLAYER + " TEXT NOT NULL, " + TABLE_COLUMN_MAX_PLAYER + " TEXT NOT NULL, " + TABLE_COLUMN_PRICE + " INTEGER NOT NULL, " + TABLE_COLUMN_CREATED_AT + " TEXT NOT NULL, " + TABLE_COLUMN_LONGITUDE + " TEXT NOT NULL, " + TABLE_COLUMN_LATITUDE + " TEXT NOT NULL)";

        db.execSQL(create_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
