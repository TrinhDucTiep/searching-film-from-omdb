package com.example.searchingfilm;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.searchingfilm.api.ApiService;
import com.example.searchingfilm.model.FilmDetail;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailFilmActivity extends AppCompatActivity {

    private static final String apikey = "2b649bd";

    private String idFilm;
    private TextView title;
    private TextView releasedDate, time, genre, director, writer,
            actors, summary, language, country, awards, rating;
    private ImageView imageView;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_film);

        title = findViewById(R.id.title_tv);
        imageView = findViewById(R.id.film_img);
        releasedDate = findViewById(R.id.released_tv);
        time = findViewById(R.id.runtime_tv);
        genre = findViewById(R.id.genre_tv);
        director = findViewById(R.id.director_tv);
        writer = findViewById(R.id.writer_tv);
        actors = findViewById(R.id.actors_tv);
        summary = findViewById(R.id.summary_tv);
        language = findViewById(R.id.language_tv);
        country = findViewById(R.id.country_tv);
        awards = findViewById(R.id.awards_tv);
        rating = findViewById(R.id.rating_tv);

        dialog = new ProgressDialog(this);
        dialog.setMessage("loading...");
        //get the imbd id of this particular film
        idFilm = getIntent().getStringExtra("id");
        dialog.show();

        ApiService.apiService.searchFilmById(idFilm, apikey)
                .enqueue(new Callback<FilmDetail>() {
                    @Override
                    public void onResponse(Call<FilmDetail> call, Response<FilmDetail> response) {
                        FilmDetail filmDetail = response.body();
                        Glide.with(DetailFilmActivity.this).load(filmDetail.getPoster()).into(imageView);
                        title.setText(filmDetail.getTitle());
                        releasedDate.setText(filmDetail.getReleased());
                        time.setText(filmDetail.getRuntime());
                        genre.setText(filmDetail.getGenre());
                        director.setText(filmDetail.getDirector());
                        writer.setText(filmDetail.getWriter());
                        actors.setText(filmDetail.getActors());
                        summary.setText(filmDetail.getPlot());
                        language.setText(filmDetail.getLanguage());
                        country.setText(filmDetail.getCountry());
                        awards.setText(filmDetail.getAwards());
                        rating.setText(filmDetail.getImdbRating());
                        dialog.dismiss();
                    }

                    @Override
                    public void onFailure(Call<FilmDetail> call, Throwable t) {
                        Toast.makeText(DetailFilmActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }
                });
    }
}