package com.example.android.basiccontentprovider.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by peterpomlett on 11/12/2017.
 */

public class NamesContentProvider extends ContentProvider {

    public static final int NAMES = 100;
    public static final int NAMES_WITH_ID = 101;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
//NAMES_WITH_ID has not  implemented yet and is never sent from any where as a uri
    public static UriMatcher buildUriMatcher() {
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(NameContract.AUTHORITY, NameContract.nameEntry.TABLE_NAME, NAMES);
        uriMatcher.addURI(NameContract.AUTHORITY, NameContract.nameEntry.TABLE_NAME + "/#", NAMES_WITH_ID);
        return uriMatcher;
    }

    NameDbHelper mNameDBHelper;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        mNameDBHelper = new NameDbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {

        final SQLiteDatabase db = mNameDBHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        Cursor returnCursor;
        switch (match) {
            case NAMES:
                returnCursor = db.query(NameContract.nameEntry.TABLE_NAME,
                        strings,
                        s,
                        strings1,
                        null,
                        null,
                        s1);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }
        returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return returnCursor;
    }


    @Nullable
    //Not used
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        final SQLiteDatabase db = mNameDBHelper.getWritableDatabase();
        int match = sUriMatcher.match(uri);
        Uri returnUri;
        switch (match) {
            case NAMES:
                long id = db.insert(NameContract.nameEntry.TABLE_NAME, null, contentValues);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(NameContract.CONTENT_URI, id);

                } else {
                    throw new android.database.SQLException("Faild to insert" + uri);
                }
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri" + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }


    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        int numRowsDeleted;
      //passing "1" as selection will clear the data base
        if (null == selection) selection = "1";
        switch (sUriMatcher.match(uri)) {
            case NAMES:
                numRowsDeleted = mNameDBHelper.getWritableDatabase().delete(
                        NameContract.nameEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return numRowsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String selection, @Nullable String[] selectionArgs) {
        SQLiteDatabase db = mNameDBHelper.getWritableDatabase();
        int rowsUpdated;
        switch (sUriMatcher.match(uri)) {
            case NAMES:
                rowsUpdated = db.update(
                        NameContract.nameEntry.TABLE_NAME,
                        contentValues,
                        selection,
                        selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsUpdated;
    }
}
