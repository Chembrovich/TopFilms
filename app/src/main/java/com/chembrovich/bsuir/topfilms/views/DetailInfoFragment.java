package com.chembrovich.bsuir.topfilms.views;


import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chembrovich.bsuir.topfilms.R;
import com.chembrovich.bsuir.topfilms.presenters.DetailInfoPresenter;
import com.chembrovich.bsuir.topfilms.presenters.interfaces.IDetailInfoPresenter;
import com.chembrovich.bsuir.topfilms.views.interfaces.IDetailInfoFragment;
import com.squareup.picasso.Picasso;

public class DetailInfoFragment extends Fragment implements IDetailInfoFragment {

    private int movieId;
    private String photoSrc;
    private String photosUserName;

    private ImageView imageView;
    private TextView photosUserNameTextView;
    private TextView movieNameTextView;
    private TextView movieTaglineTextView;
    private TextView movieGenreTextView;
    private TextView movieCountriesTextView;
    private TextView movieOriginalLanguageTextView;
    private TextView movieSpokenLanguagesTextView;
    private TextView movieProductionCompaniesTextView;
    private TextView movieStatusTextView;
    private TextView movieReleaseDateTextView;
    private TextView movieDurationTextView;
    private TextView movieOverviewTextView;
    private LinearLayout linearLayout;

    private IDetailInfoPresenter presenter;

    public DetailInfoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getInt("movie_id");
            photoSrc = getArguments().getString("photo_src");
            photosUserName = getArguments().getString("photo_user_name");
        }

        presenter = new DetailInfoPresenter(this, movieId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_detail_info, container, false);

        linearLayout = view.findViewById(R.id.layout);
        imageView = view.findViewById(R.id.detail_image_view);
        photosUserNameTextView = view.findViewById(R.id.photos_user_name_text_view);
        movieNameTextView = view.findViewById(R.id.movie_name_text_view);
        movieTaglineTextView = view.findViewById(R.id.movie_tagline_text_view);
        movieGenreTextView = view.findViewById(R.id.genre_text_view);
        movieCountriesTextView = view.findViewById(R.id.country_text_view);
        movieOriginalLanguageTextView = view.findViewById(R.id.original_language_text_view);
        movieSpokenLanguagesTextView = view.findViewById(R.id.spoken_languages_text_view);
        movieStatusTextView = view.findViewById(R.id.status_text_view);
        movieProductionCompaniesTextView = view.findViewById(R.id.production_companies_text_view);
        movieReleaseDateTextView = view.findViewById(R.id.release_date_text_view);
        movieDurationTextView = view.findViewById(R.id.duration_text_view);
        movieOverviewTextView = view.findViewById(R.id.movie_overview_text_view);

        presenter.makeRequestToGetDetailInfo();

        Picasso.with(view.getContext())
                .load(photoSrc)
                .into(imageView);
        photosUserNameTextView.setPaintFlags(photosUserNameTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        photosUserNameTextView.setText(photosUserName);
        return view;
    }

    @Override
    public void setInfo() {
        linearLayout.setVisibility(View.VISIBLE);
        movieNameTextView.setText(presenter.getMovieName());
        movieTaglineTextView.setText(presenter.getMovieTagline());
        movieGenreTextView.setText(presenter.getMovieGenre());
        movieCountriesTextView.setText(presenter.getMovieCountries());
        movieOriginalLanguageTextView.setText(presenter.getMovieOriginalLanguage());
        movieSpokenLanguagesTextView.setText(presenter.getMovieSpokenLanguages());
        movieStatusTextView.setText(presenter.getMovieStatus());
        movieProductionCompaniesTextView.setText(presenter.getMovieProductionCompanies());
        movieReleaseDateTextView.setText(presenter.getMovieReleaseDate());
        movieDurationTextView.setText(presenter.getMovieDuration());
        movieOverviewTextView.setText(presenter.getMovieOverview());
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }
}
