package com.example.mymovies.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT *FROM movies")
    LiveData<List<Movie>> getAllMovies();
    @Query("SELECT *FROM fauvorite_movies")
    LiveData<List<FavouriteMovie>> getAllFavouriteMovies();
    @Query("SELECT *FROM movies WHERE id==:movieId")
    Movie getMovieById(int movieId);
    @Query("SELECT *FROM fauvorite_movies WHERE id==:movieId")
    FavouriteMovie getFavouriteMovieById(int movieId);
    @Query("DELETE FROM movies")
    void deleteAllMovies();
    @Insert
    void insertMovie(Movie movie);
    @Insert
    void insertFavouriteMovie(FavouriteMovie movie);
    @Delete
    void deleteMovie(Movie movie);
    @Delete
    void deleteFavouriteMovie(FavouriteMovie movie);
}
