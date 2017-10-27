package com.chembrovich.bsuir.topfilms.presenters.interfaces;

public interface IDetailInfoPresenter {
    void makeRequestToGetDetailInfo();
    String getMovieName();
    String getMovieTagline();
    String getMovieGenre();
    String getMovieCountries();
    String getMovieOriginalLanguage();
    String getMovieSpokenLanguages();
    String getMovieProductionCompanies();
    String getMovieReleaseDate();
    String getMovieDuration();
    String getMovieStatus();
    String getMovieOverview();
}
