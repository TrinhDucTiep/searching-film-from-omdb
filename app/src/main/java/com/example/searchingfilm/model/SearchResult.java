package com.example.searchingfilm.model;

import java.util.ArrayList;

public class SearchResult {
    private ArrayList<Search> Search;
    private String totalResults;
    private String Response;

    public ArrayList<com.example.searchingfilm.model.Search> getSearch() {
        return Search;
    }

    public void setSearch(ArrayList<com.example.searchingfilm.model.Search> search) {
        Search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return Response;
    }

    public void setResponse(String response) {
        Response = response;
    }
}
