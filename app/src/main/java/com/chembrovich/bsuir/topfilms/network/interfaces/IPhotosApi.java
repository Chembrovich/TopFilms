package com.chembrovich.bsuir.topfilms.network.interfaces;

import com.chembrovich.bsuir.topfilms.models.photos.PhotosResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IPhotosApi {
    @GET("photos/")
    Call<List<PhotosResponse>> getPhotosList(@Query("page") int pageNumber, @Query("per_page") int pageSize);
    @GET("photos/")
    Call<List<PhotosResponse>> getPhotosList(@Query("page") int pageNumber, @Query("per_page") int pageSize,@Query("client_id") String apiKey);
}
