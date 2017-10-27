package com.chembrovich.bsuir.topfilms.presenters;

import com.chembrovich.bsuir.topfilms.models.movies.DetailMovieResponse;
import com.chembrovich.bsuir.topfilms.network.ApiHandler;
import com.chembrovich.bsuir.topfilms.network.interfaces.IApiCallback;
import com.chembrovich.bsuir.topfilms.presenters.interfaces.IDetailInfoPresenter;
import com.chembrovich.bsuir.topfilms.views.interfaces.IDetailInfoFragment;

import retrofit2.Response;

public class DetailInfoPresenter implements IDetailInfoPresenter {
    private static final String NO_INTERNET = "There is no internet connection!";
    private static final String LOAD_ERROR = "Something is wrong!";

    private IDetailInfoFragment view;
    private int movieId;
    private ApiHandler apiHandler;
    private DetailMovieResponse detailMovieInfo;

    public DetailInfoPresenter(IDetailInfoFragment view, int movieId) {
        this.view = view;
        this.movieId = movieId;
        apiHandler = new ApiHandler();
    }

    @Override
    public void makeRequestToGetDetailInfo() {
        IApiCallback<DetailMovieResponse> callback = new IApiCallback<DetailMovieResponse>() {
            @Override
            public void onResponse(Response<DetailMovieResponse> response) {
                if (response.isSuccessful()) {
                    detailMovieInfo = response.body();
                    view.setInfo();
                }
                if (response.errorBody() != null) {
                    view.showMessage(LOAD_ERROR);
                }
            }

            @Override
            public void onFailure() {
                view.showMessage(NO_INTERNET);
            }
        };
        apiHandler.getDetailMovieInfoById(movieId, callback);
    }

    @Override
    public String getMovieName() {
        return detailMovieInfo.getTitle();
    }

    @Override
    public String getMovieTagline() {
        return detailMovieInfo.getTagline();
    }

    @Override
    public String getMovieGenre() {
        StringBuilder result = new StringBuilder();
        result.append(detailMovieInfo.getGenres().get(0).getName());
        for (int i = 1; i < detailMovieInfo.getGenres().size(); i++) {
            result.append(", ");
            result.append(detailMovieInfo.getGenres().get(i).getName());
        }
        return result.toString();
    }

    @Override
    public String getMovieCountries() {
        StringBuilder result = new StringBuilder();
        result.append(detailMovieInfo.getProductionCountries().get(0).getName());
        for (int i = 1; i < detailMovieInfo.getProductionCountries().size(); i++) {
            result.append(", ");
            result.append(detailMovieInfo.getProductionCountries().get(i).getName());
        }
        return result.toString();
    }

    @Override
    public String getMovieOriginalLanguage() {
        return detailMovieInfo.getOriginalLanguage();
    }

    @Override
    public String getMovieSpokenLanguages() {
        StringBuilder result = new StringBuilder();
        result.append(detailMovieInfo.getSpokenLanguages().get(0).getName());
        for (int i = 1; i < detailMovieInfo.getSpokenLanguages().size(); i++) {
            result.append(", ");
            result.append(detailMovieInfo.getSpokenLanguages().get(i).getName());
        }
        return result.toString();
    }

    @Override
    public String getMovieProductionCompanies() {
        StringBuilder result = new StringBuilder();
        result.append(detailMovieInfo.getProductionCompanies().get(0).getName());
        for (int i = 1; i < detailMovieInfo.getProductionCompanies().size(); i++) {
            result.append(", ");
            result.append(detailMovieInfo.getProductionCompanies().get(i).getName());
        }
        return result.toString();
    }

    @Override
    public String getMovieStatus() {
        return detailMovieInfo.getStatus();
    }

    @Override
    public String getMovieReleaseDate() {
        return detailMovieInfo.getReleaseDate();
    }

    @Override
    public String getMovieDuration() {
        int durationInMinutes = detailMovieInfo.getDuration();

        if (durationInMinutes < 60) {
            return String.valueOf(durationInMinutes + " minutes");
        } else {
            return String.valueOf(durationInMinutes / 60 + " hours " + durationInMinutes % 60 + " minutes");
        }
    }

    @Override
    public String getMovieOverview() {
        return detailMovieInfo.getOverview();
    }
}
