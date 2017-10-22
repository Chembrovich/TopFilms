package com.chembrovich.bsuir.topfilms.views;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chembrovich.bsuir.topfilms.R;

public class MainActivity extends AppCompatActivity {

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
}
