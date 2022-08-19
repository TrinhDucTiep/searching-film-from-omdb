package com.example.searchingfilm.api;

import com.example.searchingfilm.model.FilmDetail;
import com.example.searchingfilm.model.SearchResult;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    //link API: https://www.omdbapi.com/?t=batman&plot=full&apikey=2b649bd
    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();


    ApiService apiService = new Retrofit.Builder()
            .baseUrl("https://www.omdbapi.com")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    @GET("/") //put the method from domain here
    Call<FilmDetail> searchFilmById(@Query("i" ) String id,
                                @Query("apikey" ) String apikey);

    @GET("/")
    Call<SearchResult> searchListFilm(@Query("s") String title,
                                      @Query("y") String year,
                                      @Query("page") int page,
                                      @Query("apikey") String apikey);
}
