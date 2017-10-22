package com.chembrovich.bsuir.topfilms.network;

import com.chembrovich.bsuir.topfilms.models.movies.MoviesResponse;
import com.chembrovich.bsuir.topfilms.models.photos.PhotosResponse;
import com.chembrovich.bsuir.topfilms.network.interfaces.IApiCallback;
import com.chembrovich.bsuir.topfilms.network.interfaces.IMoviesApi;
import com.chembrovich.bsuir.topfilms.network.interfaces.IPhotosApi;

import java.io.IOException;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiHandler {
    private IMoviesApi filmsApi;
    private IPhotosApi photosApi;
    private static final String FILMS_API_BASE_URL = "https://api.themoviedb.org/3/";
    private static final String PHOTOS_API_BASE_URL = "https://api.unsplash.com/";

    public ApiHandler() {
        final String FILMS_API_KEY = "34871c178404cb46da0d1b8412e58ad7";
        final String PHOTOS_API_KEY = "f5e8a7bd4cc7e2aa5d7963b6baa6ba1da27ef6a7fd53bdcb55a080105a09851f";

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl httpUrl = original.url();
                        HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter("api_key", FILMS_API_KEY).build();
                        Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(FILMS_API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        filmsApi = retrofit.create(IMoviesApi.class);

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        HttpUrl httpUrl = original.url();
                        HttpUrl newHttpUrl = httpUrl.newBuilder().addQueryParameter("client_id", PHOTOS_API_KEY).build();
                        Request.Builder requestBuilder = original.newBuilder().url(newHttpUrl);
                        Request request = requestBuilder.build();
                        return chain.proceed(request);
                    }
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(PHOTOS_API_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        photosApi = retrofit.create(IPhotosApi.class);
    }

    public void getTopRatedMovies(int pageNumber, final IApiCallback<MoviesResponse> callback) {
        filmsApi.getTopRatedMovies(pageNumber).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(Call<MoviesResponse> call, retrofit2.Response<MoviesResponse> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<MoviesResponse> call, Throwable t) {
                callback.onFailure();
            }
        });
    }

    public void getPhotosList(int pageNumber, int pageSize, final IApiCallback<List<PhotosResponse>> callback) {
        photosApi.getPhotosList(pageNumber, pageSize).enqueue(new Callback<List<PhotosResponse>>() {
            @Override
            public void onResponse(Call<List<PhotosResponse>> call, retrofit2.Response<List<PhotosResponse>> response) {
                callback.onResponse(response);
            }

            @Override
            public void onFailure(Call<List<PhotosResponse>> call, Throwable t) {

                callback.onFailure();
            }
        });
    }
}
