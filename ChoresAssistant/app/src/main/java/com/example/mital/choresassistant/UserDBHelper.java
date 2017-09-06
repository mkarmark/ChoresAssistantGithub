package com.example.mital.choresassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mital on 9/5/2017.
 */

public class UserDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SQLChores.db";
    private static final int DATABASE_VERSION = 1;
    public static final String USER_TABLE_NAME = "user_table";
    public static final String USER_COLUMN_ID = "user_id";
    public static final String USER_COLUMN_NAME = "user_name";
    public static final String USER_COLUMN_EMAIL = "user_email";
    public static final String USER_COLUMN_PHONE = "user_phone";
    public static final String USER_COLUMN_ADDRESS = "user_address";

    public UserDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + USER_TABLE_NAME + "(" +
                USER_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                USER_COLUMN_NAME + " TEXT, " +
                USER_COLUMN_EMAIL + " TEXT, " +
                USER_COLUMN_PHONE + " TEXT, " +
                USER_COLUMN_ADDRESS + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertUser(String name, String email) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_NAME, name);
        contentValues.put(USER_COLUMN_EMAIL, email);
        contentValues.put(USER_COLUMN_PHONE, "");
        contentValues.put(USER_COLUMN_ADDRESS, "");
        db.insert(USER_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getUserIDByUserEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + USER_TABLE_NAME + " WHERE " +
                USER_COLUMN_EMAIL + "=?", new String[] { email } );
        return res;
    }

    public Cursor getUserEmailPhoneAddressByUserID(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + USER_TABLE_NAME + " WHERE " +
                USER_COLUMN_EMAIL + "=?", new String[] { Integer.toString(userID) } );
        return res;
    }

    public boolean updateUserPhoneNumber(int userID, String phoneNumber) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_PHONE, phoneNumber);
        String whereClause = USER_COLUMN_ID + "=?";
        String[] whereArgs = new String[] { Integer.toString(userID) };
        db.update(USER_TABLE_NAME, contentValues, whereClause, whereArgs);
        return true;
    }

    public boolean updateUserAddress(int userID, String address) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_COLUMN_PHONE, address);
        String whereClause = USER_COLUMN_ID + "=?";
        String[] whereArgs = new String[] { Integer.toString(userID) };
        db.update(USER_TABLE_NAME, contentValues, whereClause, whereArgs);
        return true;
    }


}
