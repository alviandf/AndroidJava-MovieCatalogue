package com.dicoding.picodiploma.favoritekatalogfilm;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

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

    ImageView imgPoster;
    TextView tvTitle;
    TextView tvVoteAverage;
    TextView tvOverview;
    TextView tvReleaseDate;

    private MovieItem movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imgPoster = findViewById(R.id.img_poster);
        tvTitle = findViewById(R.id.tv_title);
        tvVoteAverage = findViewById(R.id.tv_rating);
        tvOverview = findViewById(R.id.tv_overview);
        tvReleaseDate = findViewById(R.id.tv_release_date);

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null){

                if(cursor.moveToFirst()) movie = new MovieItem(cursor);
                cursor.close();
            }
        }

        id = String.valueOf(movie.getId());
        title = movie.getTitle();
        voteAverage = String.valueOf(movie.getVoteAverage());
        overview = movie.getOverview();
        posterPath = movie.getPosterPath();
        releaseDate = movie.getReleaseDate();

        Glide.with(this).load("https://image.tmdb.org/t/p/w185/" + posterPath).into(imgPoster);
        tvTitle.setText(title);
        tvReleaseDate.setText(releaseDate);
        tvVoteAverage.setText(voteAverage);
        tvOverview.setText(overview);
    }
}
