package com.example.android.basiccontentprovider.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * DATA BASE HELPER
 * Created by peterpomlett on 10/12/2017.
 */

public class NameDbHelper extends SQLiteOpenHelper {
//very basic db only contains a name
    private static final String DATABASE_NAME = "nameTable.db";
    private static final int DATABASE_VERSION = 1;

    NameDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_NAME_TABLE = "CREATE TABLE " + NameContract.nameEntry.TABLE_NAME + " (" +
                NameContract.nameEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                NameContract.nameEntry.COLUMN_TITLE + " TEXT NOT NULL " +
                "); ";
        sqLiteDatabase.execSQL(SQL_CREATE_NAME_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + NameContract.nameEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
