package com.chembrovich.bsuir.topfilms.presenters;

import com.chembrovich.bsuir.topfilms.models.movies.MoviesResponse;
import com.chembrovich.bsuir.topfilms.models.movies.MoviesResponseItem;
import com.chembrovich.bsuir.topfilms.models.photos.PhotosResponse;
import com.chembrovich.bsuir.topfilms.network.interfaces.IApiCallback;
import com.chembrovich.bsuir.topfilms.presenters.interfaces.IElementsListPresenter;
import com.chembrovich.bsuir.topfilms.views.interfaces.IElementsListView;
import com.chembrovich.bsuir.topfilms.network.ApiHandler;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class ElementsListPresenter implements IElementsListPresenter {
    private static final int PAGE_SIZE = 20;

    private IElementsListView elementsListView;
    private ApiHandler apiHandler;
    private MoviesResponse moviesResponse;
    private List<PhotosResponse> photosList;

    public ElementsListPresenter(IElementsListView elementsListView) {
        this.elementsListView = elementsListView;
        apiHandler = new ApiHandler();
        moviesResponse = new MoviesResponse();
        photosList = new ArrayList<>();
    }

    @Override
    public void makeRequestToGetTopRatedMovies(int pageNumber) {
        IApiCallback<MoviesResponse> callback = new IApiCallback<MoviesResponse>() {
            @Override
            public void onResponse(Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    moviesResponse = response.body();
                }
                if (response.errorBody() != null) {
                    elementsListView.showMessage("Something is wrong!");
                }
            }

            @Override
            public void onFailure() {
                elementsListView.showMessage("There is no internet connection!");
            }
        };
        apiHandler.getTopRatedMovies(pageNumber, callback);
    }

    @Override
    public void makeRequestToGetPhotos(int pageNumber) {
        IApiCallback<List<PhotosResponse>> callback = new IApiCallback<List<PhotosResponse>>() {
            @Override
            public void onResponse(Response<List<PhotosResponse>> response) {
                if (response.isSuccessful()) {
                    photosList = response.body();
                    elementsListView.updateList();
                }
                if (response.errorBody() != null) {
                    elementsListView.showMessage("Something is wrong!");
                }
            }

            @Override
            public void onFailure() {
                elementsListView.showMessage("There is no internet connection!");
            }
        };
        apiHandler.getPhotosList(pageNumber, PAGE_SIZE, callback);

    }
    @Override
    public List<MoviesResponseItem> getMoviesList() {
        return moviesResponse.getMoviesList();
    }

    @Override
    public List<PhotosResponse> getPhotosList() {
        return photosList;
    }
}
