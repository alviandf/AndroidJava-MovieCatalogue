package com.dicoding.picodiploma.favoritekatalogfilm;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import static com.dicoding.picodiploma.favoritekatalogfilm.DatabaseContract.getColumnInt;
import static com.dicoding.picodiploma.favoritekatalogfilm.DatabaseContract.getColumnString;

public class MovieItem implements Parcelable {
    private int id;
    private String title, overview, releaseDate, voteAverage, posterPath;

    public MovieItem(int id, String title, String overview, String releaseDate, String voteAverage, String posterPath) {
        this.id = id;
        this.title = title;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public MovieItem(Cursor cursor){
        this.id = getColumnInt(cursor, DatabaseContract.MovieColumns.ID);
        this.title = getColumnString(cursor, DatabaseContract.MovieColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.MovieColumns.OVERVIEW);
        this.posterPath = getColumnString(cursor, DatabaseContract.MovieColumns.POSTER_PATH);
        this.releaseDate = getColumnString(cursor, DatabaseContract.MovieColumns.RELEASE_DATE);
        this.voteAverage = getColumnString(cursor, DatabaseContract.MovieColumns.VOTE_AVERAGE);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.overview);
        dest.writeString(this.releaseDate);
        dest.writeString(this.voteAverage);
        dest.writeString(this.posterPath);
    }

    public MovieItem() {
    }

    protected MovieItem(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.overview = in.readString();
        this.releaseDate = in.readString();
        this.voteAverage = in.readString();
        this.posterPath = in.readString();
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel source) {
            return new MovieItem(source);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };
}
