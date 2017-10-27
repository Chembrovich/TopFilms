package com.chembrovich.bsuir.topfilms.network.interfaces;

import com.chembrovich.bsuir.topfilms.models.movies.DetailMovieResponse;
import com.chembrovich.bsuir.topfilms.models.movies.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;


public interface IMoviesApi {
    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("page") int pageNumber);

    @GET("movie/{movie_id}")
    Call<DetailMovieResponse> getDetailMovieInfoById(@Path("movie_id") int movieId);
}
