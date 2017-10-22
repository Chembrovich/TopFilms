package com.chembrovich.bsuir.topfilms.network.interfaces;

import com.chembrovich.bsuir.topfilms.models.movies.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface IMoviesApi {
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("page") int pageNumber);
}
