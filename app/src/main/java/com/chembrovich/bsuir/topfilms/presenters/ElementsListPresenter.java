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
    private List<MoviesResponseItem> moviesList;
    private List<PhotosResponse> photosList;
    private int pageNumber;
    private int maxPageCount;

    public ElementsListPresenter(IElementsListView elementsListView) {
        this.elementsListView = elementsListView;
        apiHandler = new ApiHandler();
        moviesList = new ArrayList<>();
        photosList = new ArrayList<>();
        pageNumber = 1;
    }

    @Override
    public void makeRequestToGetTopRatedMovies(int pageNumber) {
        IApiCallback<MoviesResponse> callback = new IApiCallback<MoviesResponse>() {
            @Override
            public void onResponse(Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    moviesList = response.body().getMoviesList();
                    maxPageCount = response.body().getTotalPages();
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
    public void makeRequestToLoadMoreData() {
        IApiCallback<MoviesResponse> callback = new IApiCallback<MoviesResponse>() {
            @Override
            public void onResponse(Response<MoviesResponse> response) {
                if (response.isSuccessful()) {
                    moviesList.addAll(response.body().getMoviesList());
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
        pageNumber++;
        apiHandler.getTopRatedMovies(pageNumber, callback);

        IApiCallback<List<PhotosResponse>> callback1 = new IApiCallback<List<PhotosResponse>>() {
            @Override
            public void onResponse(Response<List<PhotosResponse>> response) {
                if (response.isSuccessful()) {
                    photosList.addAll(response.body());
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
        apiHandler.getPhotosList(pageNumber, PAGE_SIZE, callback1);
    }

    @Override
    public List<MoviesResponseItem> getMoviesList() {
        return moviesList;
    }

    @Override
    public List<PhotosResponse> getPhotosList() {
        return photosList;
    }

    @Override
    public int getMovieIdByPosition(int position) {
        return moviesList.get(position).getId();
    }

    @Override
    public String getPhotoSrcByPosition(int position) {
        return photosList.get(position).getUrls().getRegular();
    }

    @Override
    public String getPhotoUserNameByPosition(int position) {
        return photosList.get(position).getUser().getName();
    }

    @Override
    public int getMaxPageCount() {
        return this.maxPageCount;
    }
}
