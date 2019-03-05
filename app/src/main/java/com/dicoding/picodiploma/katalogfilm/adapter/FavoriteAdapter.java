package com.dicoding.picodiploma.katalogfilm.adapter;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.dicoding.picodiploma.katalogfilm.activity.FavoriteDetailActivity;
import com.dicoding.picodiploma.katalogfilm.click.CustomOnItemClickListener;
import com.dicoding.picodiploma.katalogfilm.model.Movie;

import java.util.ArrayList;

import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.CONTENT_URI;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.MovieViewholder>{
    private Cursor listFavorites;
    private Activity activity;
    ArrayList<Movie> listMovie;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setListFavorites(Cursor listFavorites) {
        this.listFavorites = listFavorites;
    }

    @Override
    public MovieViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movies, parent, false);
        return new MovieViewholder(view);
    }

    @Override
    public void onBindViewHolder(MovieViewholder holder, int position) {
            final Movie movies = getItem(position);
            Glide.with(activity).load("https://image.tmdb.org/t/p/w185/" + movies.getPosterPath()).into(holder.moviePoster);
            holder.movieTitle.setText(movies.getTitle());
            holder.data.setText(movies.getReleaseDate());
            holder.movieDescription.setText(movies.getOverview());
            holder.rating.setText(String.valueOf(movies.getVoteAverage()));
            try {
                holder.moviesLayout.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
                    @Override
                    public void onItemClicked(View view, int position) {
                        Intent intent = new Intent(activity, FavoriteDetailActivity.class);

                        Uri uri = Uri.parse(CONTENT_URI + "/" + movies.getId());
                        intent.setData(uri);
                        activity.startActivityForResult(intent, FavoriteDetailActivity.REQUEST_UPDATE);
                    }
                }));
            } catch(Exception e){
                e.printStackTrace();
            }
    }

    @Override
    public int getItemCount() {
        if (listMovie == null && listFavorites != null) {
            return listFavorites.getCount();
        } else if (listMovie != null) {
            Log.d("werwer", "getItemCount: "+listMovie.size());
            return listMovie.size();
        }
        return 0;
    }

    private Movie getItem(int position){
        if (!listFavorites.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Movie(listFavorites);
    }

    class MovieViewholder extends RecyclerView.ViewHolder{
        LinearLayout moviesLayout;
        ImageView moviePoster;
        TextView movieTitle;
        TextView data;
        TextView movieDescription;
        TextView rating;

        MovieViewholder(View itemView) {
            super(itemView);
            moviesLayout = itemView.findViewById(R.id.movies_layout);
            moviePoster =  itemView.findViewById(R.id.poster_image);
            movieTitle = itemView.findViewById(R.id.title);
            data = itemView.findViewById(R.id.subtitle);
            movieDescription = itemView.findViewById(R.id.description);
            rating = itemView.findViewById(R.id.rating);
        }
    }
}
