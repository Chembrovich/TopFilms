package com.chembrovich.bsuir.topfilms.views;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.chembrovich.bsuir.topfilms.R;
import com.chembrovich.bsuir.topfilms.views.interfaces.IOnRecycleViewItemClickListener;

public class MainActivity extends AppCompatActivity implements ElementsListFragment.OnElementsListFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.elements_list_fragment) != null) {
            if (savedInstanceState != null) {
                return;
            }
            ElementsListFragment elementsListFragment= new ElementsListFragment();
            elementsListFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().add(R.id.elements_list_fragment, elementsListFragment).commit();
        }
    }

    @Override
    public void onRecycleViewItemClick(int movieId, String photoSrc, String photosUserName) {
        DetailInfoFragment detailFragment = new DetailInfoFragment();

        Bundle args = new Bundle();
        args.putInt("movie_id", movieId);
        args.putString("photo_src", photoSrc);
        args.putString("photo_user_name", photosUserName);
        detailFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.elements_list_fragment, detailFragment);
        transaction.addToBackStack(null).commit();
    }

}
