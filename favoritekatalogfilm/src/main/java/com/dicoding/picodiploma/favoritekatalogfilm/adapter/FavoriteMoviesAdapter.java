package com.dicoding.picodiploma.favoritekatalogfilm.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import com.dicoding.picodiploma.favoritekatalogfilm.R;

import static com.dicoding.picodiploma.favoritekatalogfilm.DatabaseContract.MovieColumns.OVERVIEW;
import static com.dicoding.picodiploma.favoritekatalogfilm.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.dicoding.picodiploma.favoritekatalogfilm.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.dicoding.picodiploma.favoritekatalogfilm.DatabaseContract.MovieColumns.TITLE;
import static com.dicoding.picodiploma.favoritekatalogfilm.DatabaseContract.MovieColumns.VOTE_AVERAGE;
import static com.dicoding.picodiploma.favoritekatalogfilm.DatabaseContract.getColumnString;

public class FavoriteMoviesAdapter extends CursorAdapter {

    public FavoriteMoviesAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movies, viewGroup, false);
        return view;
    }


    @Override
    public Cursor getCursor() {
        return super.getCursor();
    }


    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null){
            LinearLayout moviesLayout;
            ImageView moviePoster;
            TextView movieTitle;
            TextView data;
            TextView movieDescription;
            TextView rating;

            moviesLayout = view.findViewById(R.id.movies_layout);
            moviePoster =  view.findViewById(R.id.poster_image);
            movieTitle = view.findViewById(R.id.title);
            data = view.findViewById(R.id.subtitle);
            movieDescription = view.findViewById(R.id.description);
            rating = view.findViewById(R.id.rating);;

            Glide.with(context).load("https://image.tmdb.org/t/p/w185/" + getColumnString(cursor, POSTER_PATH)).into(moviePoster);
            movieTitle.setText(getColumnString(cursor,TITLE));
            data.setText(getColumnString(cursor,RELEASE_DATE));
            movieDescription.setText(getColumnString(cursor,OVERVIEW));
            rating.setText(getColumnString(cursor,VOTE_AVERAGE));
        }
    }
}
