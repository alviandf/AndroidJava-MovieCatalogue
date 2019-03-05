package com.dicoding.picodiploma.favoritekatalogfilm;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static String TABLE_FAVORITE_MOVIE = "movie";

    public static final class MovieColumns implements BaseColumns {
        public static String ID = "id";
        public static String TITLE = "title";
        public static String VOTE_AVERAGE = "vote_average";
        public static String OVERVIEW = "overview";
        public static String POSTER_PATH = "poster_path";
        public static String RELEASE_DATE = "release_date";
    }

    public static final String AUTHORITY = "com.dicoding.picodiploma.katalogfilm";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVORITE_MOVIE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}

