package com.dicoding.picodiploma.katalogfilm.fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.dicoding.picodiploma.katalogfilm.BuildConfig;
import com.dicoding.picodiploma.katalogfilm.R;
import com.dicoding.picodiploma.katalogfilm.activity.MainActivity;
import com.dicoding.picodiploma.katalogfilm.adapter.FavoriteAdapter;
import com.dicoding.picodiploma.katalogfilm.model.Movie;

import java.util.ArrayList;

import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.CONTENT_URI;

public class FavoriteFragment extends Fragment {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = BuildConfig.TMDB_API_KEY;;

    private ArrayList<Movie> movies;
    ProgressBar progressBar;

    private Cursor cList;
    private FavoriteAdapter adapter;
    RecyclerView tempRecyclerView;

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        tempRecyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new FavoriteAdapter(getActivity());
        adapter.setListFavorites(cList);
        recyclerView.setAdapter(adapter);

        new LoadMovieAsync().execute();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putParcelableArrayList("list", movies);
    }

    public class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(CONTENT_URI,null,null,null,null);
        }

        @Override
        protected void onPostExecute(Cursor movies) {
            super.onPostExecute(movies);
            //progressBar.setVisibility(View.GONE);

            cList = movies;
            adapter.setListFavorites(cList);
            progressBar.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();

            if (cList.getCount() == 0){
                //showSnackbarMessage("Tidak ada data saat ini");
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        new LoadMovieAsync().execute();
    }
}
