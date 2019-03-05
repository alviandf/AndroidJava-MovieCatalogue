package com.dicoding.picodiploma.katalogfilm.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbmovie";

    private static final int DATABASE_VERSION = 1;

    public static String VOTE_AVERAGE = "vote_average";
    public static String OVERVIEW = "overview";
    public static String POSTER_PATH = "poster_path";
    public static String RELEASE_DATE = "release_date";

    private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s STRING PRIMARY KEY," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVORITE_MOVIE,
            DatabaseContract.MovieColumns.ID,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.VOTE_AVERAGE,
            DatabaseContract.MovieColumns.OVERVIEW,
            DatabaseContract.MovieColumns.POSTER_PATH,
            DatabaseContract.MovieColumns.RELEASE_DATE
    );

    /*private static final String SQL_CREATE_TABLE_NOTE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_NOTE,
            DatabaseContract.MovieColumns._ID,
            DatabaseContract.MovieColumns.TITLE,
            DatabaseContract.MovieColumns.DESCRIPTION,
            DatabaseContract.MovieColumns.DATE
    );*/

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITE_MOVIE);
        onCreate(db);
    }
}
