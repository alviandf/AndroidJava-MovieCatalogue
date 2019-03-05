package com.dicoding.picodiploma.katalogfilm.rest;

import com.dicoding.picodiploma.katalogfilm.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface ApiService {
    @GET("movie/now_playing")
    Call<MoviesResponse> getNowPlayingMovies(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MoviesResponse> getSearchedMovies(@Query("api_key") String apiKey, @Query("query") String movieSearch);

    @GET("movie/{movie_id}")
    Call<MoviesResponse> getMovieById(@Path("movie_id") String movieID, @Query("api_key") String apiKey);

}