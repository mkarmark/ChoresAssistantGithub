package com.example.mital.choresassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by mital on 8/2/2017.
 */

public class ChoreDBHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final String DATABASE_NAME = "SQLChores.db";
    private static final int DATABASE_VERSION = 1;
    public static final String CHORE_TABLE_NAME = "chores_table";
    public static final String CHORE_COLUMN_ID = "chore_id";
    public static final String CHORE_COLUMN_TYPE_ID = "chore_type_id";
    public static final String CHORE_COLUMN_SPECIFICATIONS = "chore_specifications";
    public static final String CHORE_COLUMN_USER_ID = "chore_user_id";
    public static final String CHORE_COLUMN_IS_OFFER_MADE = "chore_is_offer_made";
    public static final String CHORE_COLUMN_OFFER_USER_ID = "chore_offer_user_id";
    public static final String CHORE_COLUMN_IS_OFFER_ACCEPTED = "chore_is_offer_accepted";
    public static final String CHORE_COLUMN_IS_CHORE_COMPLETED = "chore_is_chore_completed";


    public ChoreDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CHORE_TABLE_NAME + "(" +
                CHORE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CHORE_COLUMN_TYPE_ID + " INTEGER, " +
                CHORE_COLUMN_SPECIFICATIONS + " TEXT, " +
                CHORE_COLUMN_USER_ID + " INTEGER, " +
                CHORE_COLUMN_IS_OFFER_MADE + " INTEGER, " +
                CHORE_COLUMN_OFFER_USER_ID + " INTEGER, " +
                CHORE_COLUMN_IS_OFFER_ACCEPTED + " INTEGER, " +
                CHORE_COLUMN_IS_CHORE_COMPLETED + " INTEGER)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CHORE_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertChore(int choreTypeID, String choreSpecifications, int userID) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHORE_COLUMN_TYPE_ID, choreTypeID);
        contentValues.put(CHORE_COLUMN_SPECIFICATIONS, choreSpecifications);
        contentValues.put(CHORE_COLUMN_USER_ID, userID);
        contentValues.put(CHORE_COLUMN_IS_OFFER_MADE, 0);
        contentValues.put(CHORE_COLUMN_OFFER_USER_ID, -1);
        contentValues.put(CHORE_COLUMN_IS_OFFER_ACCEPTED, 0);
        contentValues.put(CHORE_COLUMN_IS_CHORE_COMPLETED, 0);
        db.insert(CHORE_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getChoresPostedByUserID(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CHORE_TABLE_NAME + " WHERE " +
                CHORE_COLUMN_USER_ID + "=?", new String[] { Integer.toString(userID) } );
        return res;
    }

    public Cursor getChoresOfferedAndOrCompletedByUserID(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CHORE_TABLE_NAME + " WHERE " +
                CHORE_COLUMN_OFFER_USER_ID + "=?", new String[] { Integer.toString(userID) } );
        return res;
    }

    public Cursor getChoresOfferedAndNotCompletedByUserID(int userID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CHORE_TABLE_NAME + " WHERE " +
                CHORE_COLUMN_OFFER_USER_ID + "=? AND" + CHORE_COLUMN_IS_CHORE_COMPLETED + "=0", new String[] { Integer.toString(userID) } );
        return res;
    }

    public Cursor getAllChores() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CHORE_TABLE_NAME, null );
        return res;
    }
}
