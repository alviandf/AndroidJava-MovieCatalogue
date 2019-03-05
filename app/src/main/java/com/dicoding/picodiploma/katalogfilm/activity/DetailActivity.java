package com.dicoding.picodiploma.katalogfilm.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dicoding.picodiploma.katalogfilm.R;
import com.dicoding.picodiploma.katalogfilm.db.MovieHelper;
import com.dicoding.picodiploma.katalogfilm.model.Movie;

import static android.provider.BaseColumns._ID;
import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.CONTENT_URI;
import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.MovieColumns.ID;
import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.MovieColumns.TITLE;
import static com.dicoding.picodiploma.katalogfilm.db.DatabaseContract.MovieColumns.VOTE_AVERAGE;

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIE= "extra_movie";

    public static String EXTRA_NOTE = "extra_note";
    public static String EXTRA_POSITION = "extra_position";

    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    public static String id;
    public static String title;
    public static String voteAverage;
    public static String overview;
    public static String posterPath;
    public static String releaseDate;

    Button btnDelete;
    ImageView imgPoster;
    TextView tvTitle;
    TextView tvVoteAverage;
    TextView tvOverview;
    TextView tvReleaseDate;

    Movie movie;
    MovieHelper movieHelper;

    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        btnDelete = findViewById(R.id.btn_delete);
        imgPoster = findViewById(R.id.img_poster);
        tvTitle = findViewById(R.id.tv_title);
        tvVoteAverage = findViewById(R.id.tv_rating);
        tvOverview = findViewById(R.id.tv_overview);
        tvReleaseDate = findViewById(R.id.tv_release_date);

        btnDelete.setOnClickListener(this);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        id = String.valueOf(movie.getId());
        title = movie.getTitle();
        voteAverage = String.valueOf(movie.getVoteAverage());
        overview = movie.getOverview();
        posterPath = movie.getPosterPath();
        releaseDate = movie.getReleaseDate();

        position = getIntent().getIntExtra(EXTRA_POSITION, 0);

        Glide.with(this).load("https://image.tmdb.org/t/p/w185/" + posterPath).into(imgPoster);
        tvTitle.setText(title);
        tvReleaseDate.setText(releaseDate);
        tvVoteAverage.setText(voteAverage);
        tvOverview.setText(overview);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_favorite) {
            Toast.makeText(DetailActivity.this, "Favorit berhasil ditambahkan", Toast.LENGTH_LONG).show();
            ContentValues values = new ContentValues();
            values.put(_ID,id);
            values.put(ID,id);
            values.put(OVERVIEW, overview);
            values.put(POSTER_PATH, posterPath);
            values.put(RELEASE_DATE, releaseDate);
            values.put(VOTE_AVERAGE, voteAverage);
            values.put(TITLE, title);
            getContentResolver().insert(CONTENT_URI,values);
            setResult(RESULT_ADD);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_delete){
            movieHelper.delete(movie.getId());
            Intent intent = new Intent();
            intent.putExtra(EXTRA_POSITION, position);
            setResult(RESULT_DELETE, intent);
            finish();
        }
    }
}
