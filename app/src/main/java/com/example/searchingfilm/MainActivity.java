package com.example.searchingfilm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.searchingfilm.api.ApiService;
import com.example.searchingfilm.model.FilmDetail;
import com.example.searchingfilm.model.Search;
import com.example.searchingfilm.model.SearchResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String apikey = "2b649bd";
    private String titleStr, yearStr;

    private EditText titleEdt, yearEdt;
    private Button searchBtn;
    private RecyclerView recyclerFilm;
    private FilmAdapter filmAdapter;

    private boolean isLoading;
    private boolean isLastPage;
    private int totalPage;
    private int currentPage=1;

    private SearchResult searchResult;
    private ArrayList<Search> listFilm;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titleEdt = findViewById(R.id.title_edt);
        yearEdt = findViewById(R.id.year_edt);
        searchBtn = findViewById(R.id.search_btn);
        recyclerFilm = findViewById(R.id.recycler_film);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Searching...");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerFilm.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerFilm.addItemDecoration(itemDecoration);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filmAdapter = new FilmAdapter();
                recyclerFilm.setAdapter(filmAdapter);
                titleStr = titleEdt.getText().toString();
                yearStr = yearEdt.getText().toString();
                currentPage = 1;
                isLoading = false;
                isLastPage = false;
                listFilm = new ArrayList<>();
                searchResult = new SearchResult();
                progressDialog.show();
                setFirstData();
            }
        });

        recyclerFilm.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            public void loadMoreItem() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }
        });

    }

    //load data page 1
    private void setFirstData(){
        getListFilm();
    }

    private void getListFilm(){
//        Toast.makeText(this, "Loading data page " + currentPage, Toast.LENGTH_SHORT).show();
        ApiService.apiService.searchListFilm(titleStr, yearStr, currentPage, apikey)
                .enqueue(new Callback<SearchResult>() {
                    @Override
                    public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
//                        System.out.println("Đã load xong...............................................");
                        searchResult = response.body();

                        if(progressDialog.isShowing()) progressDialog.dismiss();

                        if(searchResult.getResponse().equals("False")){
                            Toast.makeText(MainActivity.this, "No searching result!!!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        totalPage = (Integer.parseInt(searchResult.getTotalResults()) / 10) + 1;

                        if (currentPage == 1) {
                            listFilm.addAll(searchResult.getSearch());
                            filmAdapter.setData(searchResult.getSearch());
                            if (currentPage < totalPage){
                                filmAdapter.addFooterLoading();
                            } else {
                                isLastPage = true;
                            }
                        }else {
                            //todo
                            filmAdapter.removeFooterLoading();
                            listFilm.addAll(searchResult.getSearch());
                            filmAdapter.setData(listFilm);
                            isLoading = false;
                            if(currentPage < totalPage){
                                filmAdapter.addFooterLoading();
                            } else {
                                isLastPage = true;
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<SearchResult> call, Throwable t) {
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                });

    }

    private void loadNextPage(){
        getListFilm();
    }

}