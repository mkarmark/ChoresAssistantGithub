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
    public static final String CHORE_TABLE_NAME = "chores";
    public static final String CHORE_COLUMN_ID = "_id";
    public static final String CHORE_COLUMN_TYPE = "chore";
    public static final String CHORE_COLUMN_SPECIFICATIONS = "specifications";
    public static final String CHORE_COLUMN_DATE = "date";
    public static final String CHORE_COLUMN_TIME = "time";
    public static final String CHORE_COLUMN_EMAIL = "email";
    public static final String CHORE_COLUMN_PHONENUMBER = "phonenumber";
    public static final String CHORE_COLUMN_LOCATION = "location";

    public static final String PERSON_COLUMN_NAME = "name";
    public static final String PERSON_COLUMN_GENDER = "gender";
    public static final String PERSON_COLUMN_AGE = "age";

    public ChoreDBHelper(Context context) {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + CHORE_TABLE_NAME + "(" +
                CHORE_COLUMN_ID + " INTEGER PRIMARY KEY, " +
                CHORE_COLUMN_TYPE + " TEXT, " +
                CHORE_COLUMN_SPECIFICATIONS + " TEXT, " +
                CHORE_COLUMN_DATE + " TEXT, " +
                CHORE_COLUMN_TIME + " TEXT, " +
                CHORE_COLUMN_EMAIL + " TEXT, " +
                CHORE_COLUMN_PHONENUMBER + " TEXT, " +
                CHORE_COLUMN_LOCATION + " TEXT)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CHORE_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertChore(String type, String specifications, String date, String time, String email, String phoneNumber, String location) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(CHORE_COLUMN_TYPE, type);
        contentValues.put(CHORE_COLUMN_SPECIFICATIONS, specifications);
        contentValues.put(CHORE_COLUMN_DATE, date);
        contentValues.put(CHORE_COLUMN_TIME, time);
        contentValues.put(CHORE_COLUMN_EMAIL, email);
        contentValues.put(CHORE_COLUMN_PHONENUMBER, phoneNumber);
        contentValues.put(CHORE_COLUMN_LOCATION, location);
        db.insert(CHORE_TABLE_NAME, null, contentValues);
        return true;
    }

//    public boolean updatePerson(Integer id, String name, String gender, int age) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(PERSON_COLUMN_NAME, name);
//        contentValues.put(PERSON_COLUMN_GENDER, gender);
//        contentValues.put(PERSON_COLUMN_AGE, age);
//        db.update(PERSON_TABLE_NAME, contentValues, PERSON_COLUMN_ID + " = ? ", new String[] { Integer.toString(id) } );
//        return true;
//    }

    public Cursor getChoresByUserID(String email, String phoneNumber) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CHORE_TABLE_NAME + " WHERE " +
                CHORE_COLUMN_EMAIL + "=? AND " + CHORE_COLUMN_PHONENUMBER + "=?", new String[] { email, phoneNumber } );
        return res;
    }
    public Cursor getAllChores() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery( "SELECT * FROM " + CHORE_TABLE_NAME, null );
        return res;
    }

//    public Integer deletePerson(Integer id) {
//        SQLiteDatabase db = this.getWritableDatabase();
//        return db.delete(PERSON_TABLE_NAME,
//                PERSON_COLUMN_ID + " = ? ",
//                new String[] { Integer.toString(id) });
//    }
}
