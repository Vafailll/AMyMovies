package com.example.mymovies.data;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {
    private static MovieDataBase dataBase;
    LiveData<List<Movie>>movies;
    LiveData<List<FavouriteMovie>>favouriteMovies;

    public LiveData<List<Movie>> getMovies() {
        return movies;
    }

    public LiveData<List<FavouriteMovie>> getFavouriteMovies() {
        return favouriteMovies;
    }

    public MainViewModel(@NonNull Application application) {
        super(application);
        dataBase=MovieDataBase.getInstance(getApplication());
        movies=dataBase.movieDao().getAllMovies();
        favouriteMovies=dataBase.movieDao().getAllFavouriteMovies();
    }
    public Movie getMovieById(int id){
        try {
            return new GetMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public FavouriteMovie getFavouriteMovieById(int id){
        try {
            return new GetFavouriteMovieTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
    public void insertFavouriteMovie(FavouriteMovie movie){
        new InsertFavouriteMovieTask().execute(movie);
    }
    public void deleteFavouriteMovie(FavouriteMovie movie){
        new DeleteFavouriteTask().execute(movie);
    }

    public void deleteAllMovies(){
        new DeleteMovieTask().execute();
    }
    public void insertMovie(Movie movie){
        new InsertMovieTask().execute(movie);
    }
    public void deleteMovie(Movie movie){
        new DeleteTask().execute(movie);
    }
    private static class DeleteFavouriteTask extends AsyncTask<FavouriteMovie,Void,Void>{
        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if(movies!=null&&movies.length>0) {
                dataBase.movieDao().deleteFavouriteMovie(movies[0]);
            }
            return null;
        }
    }
    private static class InsertFavouriteMovieTask extends AsyncTask<FavouriteMovie,Void,Void>{
        @Override
        protected Void doInBackground(FavouriteMovie... movies) {
            if(movies!=null&&movies.length>0) {
                dataBase.movieDao().insertFavouriteMovie(movies[0]);
            }
            return null;
        }
    }
    private static class DeleteTask extends AsyncTask<Movie,Void,Void>{
        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies!=null&&movies.length>0) {
                dataBase.movieDao().deleteMovie(movies[0]);
            }
            return null;
        }
    }
    private static class InsertMovieTask extends AsyncTask<Movie,Void,Void>{
        @Override
        protected Void doInBackground(Movie... movies) {
            if(movies!=null&&movies.length>0) {
                dataBase.movieDao().insertMovie(movies[0]);
            }
            return null;
        }
    }
    private static class DeleteMovieTask extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... integers) {
                 dataBase.movieDao().deleteAllMovies();

            return null;
        }

    }
    private static class GetFavouriteMovieTask extends AsyncTask<Integer,Void,FavouriteMovie>{
        @Override
        protected FavouriteMovie doInBackground(Integer... integers) {
            if(integers!=null&&integers.length>0) {
                return dataBase.movieDao().getFavouriteMovieById(integers[0]);
            }
            return null;
        }
    }
    private static class GetMovieTask extends AsyncTask<Integer,Void,Movie>{
        @Override
        protected Movie doInBackground(Integer... integers) {
            if(integers!=null&&integers.length>0) {
                return dataBase.movieDao().getMovieById(integers[0]);
            }
            return null;
        }
    }
}
