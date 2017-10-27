package com.chembrovich.bsuir.topfilms.presenters.interfaces;

import com.chembrovich.bsuir.topfilms.models.movies.MoviesResponseItem;
import com.chembrovich.bsuir.topfilms.models.photos.PhotosResponse;

import java.util.List;

public interface IElementsListPresenter {
    void makeRequestToGetTopRatedMovies(int pageNumber);
    void makeRequestToGetPhotos(int pageNumber);
    List<PhotosResponse> getPhotosList();
    List<MoviesResponseItem> getMoviesList();
    void makeRequestToLoadMoreData();
    int getMovieIdByPosition(int position);
    String getPhotoSrcByPosition(int position);
    String getPhotoUserNameByPosition(int posititon);
}
