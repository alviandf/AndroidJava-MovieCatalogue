package com.dicoding.picodiploma.katalogfilm.fragment;


import android.content.Intent;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.dicoding.picodiploma.katalogfilm.BuildConfig;
import com.dicoding.picodiploma.katalogfilm.R;
import com.dicoding.picodiploma.katalogfilm.activity.DetailActivity;
import com.dicoding.picodiploma.katalogfilm.activity.MainActivity;
import com.dicoding.picodiploma.katalogfilm.adapter.MoviesAdapter;
import com.dicoding.picodiploma.katalogfilm.click.ItemClickSupport;
import com.dicoding.picodiploma.katalogfilm.model.Movie;
import com.dicoding.picodiploma.katalogfilm.model.MoviesResponse;
import com.dicoding.picodiploma.katalogfilm.rest.ApiClient;
import com.dicoding.picodiploma.katalogfilm.rest.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private final static String API_KEY = BuildConfig.TMDB_API_KEY;;

    private ArrayList<Movie> list = new ArrayList<>();
    RecyclerView tempRecyclerView;
    ProgressBar progressBar;

    EditText edtSearch;
    Button btnSearch;

    MoviesAdapter moviesAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelableArrayList("list", list);
        super.onSaveInstanceState(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        edtSearch = view.findViewById(R.id.edit_search);
        btnSearch = view.findViewById(R.id.btn_search);
        btnSearch.setOnClickListener(this);

        final RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        tempRecyclerView = recyclerView;
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        moviesAdapter = new MoviesAdapter(list, R.layout.item_movies, getActivity());
        moviesAdapter.setDefaultView(true);
        recyclerView.setAdapter(moviesAdapter);

        if(savedInstanceState != null){
            list = savedInstanceState.getParcelableArrayList("list");
            Log.d("werwer", String.valueOf(list.size()));
            moviesAdapter.setDefaultView(false);
            progressBar.setVisibility(View.GONE);
            moviesAdapter.setListMovie(list);
        } else {
            ApiService apiService =
                    ApiClient.getClient().create(ApiService.class);

            Call<MoviesResponse> call = apiService.getUpcomingMovies(API_KEY);

            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    list.addAll(movies);

                    Log.d(TAG, "Number of movies received: " + movies.size());
                    moviesAdapter.setDefaultView(false);
                    progressBar.setVisibility(View.GONE);
                    moviesAdapter.setListMovie(list);
                    // Log untuk test apakah data sudah masuk atau belum
                    for (Movie movie : movies) {
                        Log.d("Title", movie.getTitle());
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });

        }

        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                showSelectedMovie(list.get(position));
            }
        });
    }

    private void showSelectedMovie(Movie movie){
        movie.setTitle(movie.getTitle());
        Intent moveWithObjectIntent = new Intent(getActivity(), DetailActivity.class);
        moveWithObjectIntent.putExtra(DetailActivity.EXTRA_MOVIE, movie);
        startActivity(moveWithObjectIntent);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_search) {
            String inputSearch = edtSearch.getText().toString().trim();

            progressBar.setVisibility(View.VISIBLE);
            final RecyclerView recyclerView = tempRecyclerView;
            recyclerView.setAdapter(null);

            moviesAdapter = new MoviesAdapter(list, R.layout.item_movies, getActivity());
            moviesAdapter.setDefaultView(true);

            ApiService apiService =
                    ApiClient.getClient().create(ApiService.class);
            Call<MoviesResponse> call = apiService.getSearchedMovies(API_KEY, inputSearch);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse>call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    list.clear();
                    list.addAll(movies);

                    Log.d(TAG, "Number of movies received: " + movies.size());
                    progressBar.setVisibility(View.GONE);
                    recyclerView.setAdapter(moviesAdapter);

                    // Log untuk test apakah data sudah masuk atau belum
                    for( Movie movie : movies){
                        Log.d("Title", movie.getTitle());
                    }
                }

                @Override
                public void onFailure(Call<MoviesResponse>call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }
    }
}
