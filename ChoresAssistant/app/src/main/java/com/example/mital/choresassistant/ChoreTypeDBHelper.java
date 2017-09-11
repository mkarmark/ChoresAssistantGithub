package com.example.mital.choresassistant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by mital on 9/4/2017.
 */

public class ChoreTypeDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "SQLChores.db";
    private static final int DATABASE_VERSION = 1;
    public static final String CHORE_TYPE_TABLE_NAME = "chore_type_table";
    public static final String CHORE_TYPE_COLUMN_ID = "chore_type_id";
    public static final String CHORE_TYPE_COLUMN_NAME = "chore_type_name";

    public ChoreTypeDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CHORE_TYPE_TABLE_NAME + "(" +
                CHORE_TYPE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CHORE_TYPE_COLUMN_NAME + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CHORE_TYPE_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertChoreType(String choreTypeName) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHORE_TYPE_COLUMN_NAME, choreTypeName);
        db.insert(CHORE_TYPE_TABLE_NAME, null, contentValues);
        return true;
    }

    public Cursor getChoreTypeIDByChoreTypeName(String choreTypeName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CHORE_TYPE_TABLE_NAME + " WHERE " +
                CHORE_TYPE_COLUMN_NAME + "=?", new String[] { choreTypeName } );
        return res;
    }

    public Cursor getChoreTypeNameByChoreTypeID(int choreTypeID) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CHORE_TYPE_TABLE_NAME + " WHERE " +
                CHORE_TYPE_COLUMN_NAME + "=?", new String[] { Integer.toString(choreTypeID) } );
        return res;
    }

    public Cursor getAllChoreTypes() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CHORE_TYPE_TABLE_NAME, null );
        return res;
    }
}
