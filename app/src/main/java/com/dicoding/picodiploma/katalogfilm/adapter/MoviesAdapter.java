package com.dicoding.picodiploma.katalogfilm.adapter;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.katalogfilm.R;
import com.dicoding.picodiploma.katalogfilm.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {
    private Cursor listMovies;
    private ArrayList<Movie> arrayListMovies;
    private List<Movie> movies;
    private int rowLayout;
    private Context context;
    private Activity activity;
    private boolean isDefaultView;


    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        LinearLayout moviesLayout;
        ImageView moviePoster;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;

        public MovieViewHolder(View v) {
            super(v);

            moviesLayout = v.findViewById(R.id.movies_layout);
            moviePoster =  v.findViewById(R.id.poster_image);
            movieTitle = v.findViewById(R.id.title);
            data = v.findViewById(R.id.subtitle);
            movieDescription = v.findViewById(R.id.description);
            rating = v.findViewById(R.id.rating);
        }
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.arrayListMovies = new ArrayList<>();
        this.arrayListMovies.addAll(listMovie);
        this.notifyDataSetChanged();
    }

    public ArrayList<Movie> getListMovie() {
        return arrayListMovies;
    }

    public void setDefaultView(boolean isDefaultView) {
        this.isDefaultView = isDefaultView;

        Log.d("werwer isDefaultView", String.valueOf(this.isDefaultView));
    }

    public MoviesAdapter(List<Movie> movies, int rowLayout, Context context) {
        this.movies = movies;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public MoviesAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, final int position) {
        Log.d("werwer", "run");
        if(isDefaultView) {
            Glide.with(context).load("https://image.tmdb.org/t/p/w185/" + movies.get(position).getPosterPath()).into(holder.moviePoster);
            holder.movieTitle.setText(movies.get(position).getTitle());
            holder.data.setText(movies.get(position).getReleaseDate());
            holder.movieDescription.setText(movies.get(position).getOverview());
            holder.rating.setText(movies.get(position).getVoteAverage().toString());
        } else {
            Log.d("werwer", "onBindViewHolder: " + getListMovie().get(position));
            Glide.with(context).load("https://image.tmdb.org/t/p/w185/" + getListMovie().get(position).getPosterPath()).into(holder.moviePoster);
            holder.movieTitle.setText(getListMovie().get(position).getTitle());
            holder.data.setText(getListMovie().get(position).getReleaseDate());
            holder.movieDescription.setText(getListMovie().get(position).getOverview());
            holder.rating.setText(getListMovie().get(position).getVoteAverage().toString());
        }
    }

    @Override
    public int getItemCount() {
        if(arrayListMovies == null){
            return movies.size();
        } else {
           return arrayListMovies.size();
        }

    }

    private Movie getItem(int position) {
        if (!listMovies.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(listMovies);
    }
}
