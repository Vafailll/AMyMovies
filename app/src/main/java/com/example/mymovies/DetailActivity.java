package com.example.mymovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymovies.data.FavouriteMovie;
import com.example.mymovies.data.MainViewModel;
import com.example.mymovies.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private ImageView imageViewAddToFavourite;
    private ImageView imageViewBigPoster;
    private TextView textViewTitle;
    private TextView textViewOrginalTitle;
    private TextView textViewRaiting;
    private TextView textViewReleaseDate;
    private TextView textViewOverView;
    private int id;
    private MainViewModel viewModel;
    private Movie movie;

    private FavouriteMovie favouriteMovie;
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        switch (id){
            case R.id.itemMain:
                Intent intent=new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.itemFavourite:
                Intent intentFavourite=new Intent(this, FavouriteActivity.class);
                startActivity(intentFavourite);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        imageViewBigPoster=(ImageView)findViewById(R.id.imageViewBigPoster);
        textViewTitle=(TextView)findViewById(R.id.textViewTitle);
        textViewOrginalTitle=(TextView)findViewById(R.id.textViewOriginalTitle);
        textViewRaiting=(TextView)findViewById(R.id.textViewRaiting);
        textViewReleaseDate=(TextView)findViewById(R.id.textViewReleaseData);
        textViewOverView=(TextView)findViewById(R.id.textViewOverwiev);
        imageViewAddToFavourite=findViewById(R.id.imageViewAddToFavourite);
        Intent intent=getIntent();
        if(intent!=null&&intent.hasExtra("id")){
            id=intent.getIntExtra("id",-1);
        }else {
            finish();
        }
        viewModel= ViewModelProviders.of(this).get(MainViewModel.class);
        movie=viewModel.getMovieById(id);
        Picasso.get().load(movie.getPosterPath()).into(imageViewBigPoster);
        textViewTitle.setText(movie.getTitle());
        textViewOrginalTitle.setText(movie.getOriginalTitle());
        textViewRaiting.setText(Double.toString(movie.getVoteAverage()));
        textViewReleaseDate.setText(movie.getReleasedDate());
        textViewOverView.setText(movie.getOverview());
        setFavourite();
    }
    private void setFavourite(){
        favouriteMovie=viewModel.getFavouriteMovieById(id);
        if(favouriteMovie==null){
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_add_to);
        }else{
            imageViewAddToFavourite.setImageResource(R.drawable.favourite_remove);
        }
    }
    public void onClickChangeFavourite(View view) {
        if (favouriteMovie == null) {
            viewModel.insertFavouriteMovie(new FavouriteMovie(movie));
            Toast.makeText(this, "Добавлено в избранное", Toast.LENGTH_SHORT).show();
        }else{
            viewModel.deleteFavouriteMovie(favouriteMovie);
            Toast.makeText(this, "Удалено из избранного", Toast.LENGTH_SHORT).show();
        }
        setFavourite();
    }
}
