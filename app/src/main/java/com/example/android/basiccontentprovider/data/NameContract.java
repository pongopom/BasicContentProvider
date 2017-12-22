package com.example.android.basiccontentprovider.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * CONTRACT
 * Created by peterpomlett on 10/12/2017.
 */

public class NameContract {

    public static final class nameEntry implements BaseColumns {
        static final String TABLE_NAME = "nameTable";
        public static final String COLUMN_TITLE = "name";
    }

    static final String AUTHORITY = "com.example.android.basiccontentprovider";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + AUTHORITY);
    //below is what we use in our queries
    public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(nameEntry.TABLE_NAME).build();

}
