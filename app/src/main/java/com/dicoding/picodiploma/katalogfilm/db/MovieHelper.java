package com.dicoding.picodiploma.katalogfilm.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import static android.provider.MediaStore.Audio.Playlists.Members._ID;
import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.MovieColumns.ID;
import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.TABLE_FAVORITE_MOVIE;

public class MovieHelper {
    private static String DATABASE_TABLE = TABLE_FAVORITE_MOVIE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public MovieHelper(Context context){
        this.context = context;
    }

    public MovieHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }
/*
    public ArrayList<Movie> query(){
        ArrayList<Movie> arrayList = new ArrayList<Movie>();
        Cursor cursor = database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null,_ID +" DESC"
                ,null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount()>0) {
            do {

                movie = new Movie();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID)));
                /*movie.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setDescription(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));

                arrayList.add(movie);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Note note){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(TITLE, note.getTitle());
        initialValues.put(DESCRIPTION, note.getDescription());
        initialValues.put(DATE, note.getDate());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Note note){
        ContentValues args = new ContentValues();
        args.put(TITLE, note.getTitle());
        args.put(DESCRIPTION, note.getDescription());
        args.put(DATE, note.getDate());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + note.getId() + "'", null);
    }
*/
    public int delete(int id){
        return database.delete(TABLE_FAVORITE_MOVIE, ID + " = '"+id+"'", null);
    }


    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE,null
                ,ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,ID + " DESC");
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,ID + " = ?", new String[]{id});
    }
}
