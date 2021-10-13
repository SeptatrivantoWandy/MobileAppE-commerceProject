package com.example.mcsprojectakhir;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class TransactionDataDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "TransactionDataDB.db";
    public static final Integer DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "tbltransactiondata";
    public static final String TABLE_COLUMN_ID = "id";
    public static final String TABLE_COLUMN_USER_ID = "td_user_id";
    public static final String TABLE_COLUMN_PRODUCT_ID = "td_product_id";
    public static final String TABLE_COLUMN_DATE = "td_date";

    List<TransactionHistoryData> TD = new ArrayList<>();

    public TransactionDataDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void insertTD(TransactionHistoryData new_TD) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

//        cv.put(TABLE_COLUMN_ID, new_TD.getTrId());
        cv.put(TABLE_COLUMN_USER_ID, new_TD.getTrUserId());
        cv.put(TABLE_COLUMN_PRODUCT_ID, new_TD.getTrProductId());
        cv.put(TABLE_COLUMN_DATE, new_TD.getTrDate());


        db.insert(TABLE_NAME, null, cv);
    }

    Cursor readAllTransactionData () {
        SQLiteDatabase db = this.getReadableDatabase();
        String TD_query = "SELECT * FROM " + TABLE_NAME;

        Cursor cursor = null;

        if (db == null){
//            Toast.makeText((Context) cursor, "no data", Toast.LENGTH_SHORT).show();
        }

        if (db != null) {
            cursor = db.rawQuery(TD_query, null);
        }

        return cursor;
    }

    public boolean updateTD (TransactionHistoryData update_TD) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(TABLE_COLUMN_ID, update_TD.getTrId());
        cv.put(TABLE_COLUMN_USER_ID, update_TD.getTrUserId());
        cv.put(TABLE_COLUMN_PRODUCT_ID, update_TD.getTrProductId());
        cv.put(TABLE_COLUMN_DATE, update_TD.getTrDate());


        db.update(
                TABLE_NAME,
                cv,
                "id = ?",
                new String [] { update_TD.getTrId() } );

        return true;
    }


    public int deleteTD(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        return db.delete(
                TABLE_NAME,
                "id=?",
                new String[]{ String.valueOf(id) } );

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String create_table_query = "CREATE TABLE " + TABLE_NAME + " (" + TABLE_COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TABLE_COLUMN_USER_ID + " TEXT NOT NULL, " + TABLE_COLUMN_PRODUCT_ID + " TEXT NOT NULL, " + TABLE_COLUMN_DATE + " TEXT NOT NULL)";

        db.execSQL(create_table_query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
