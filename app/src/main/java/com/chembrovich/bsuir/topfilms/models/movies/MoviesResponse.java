package com.chembrovich.bsuir.topfilms.models.movies;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MoviesResponse {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("total_results")
    @Expose
    private int totalResults;
    @SerializedName("total_pages")
    @Expose
    private int totalPages;
    @SerializedName("results")
    @Expose
    private List<MoviesResponseItem> moviesList;

    public MoviesResponse() {
        this.moviesList = new ArrayList<>();
    }

    public List<MoviesResponseItem> getMoviesList() {
        return moviesList;
    }
}
