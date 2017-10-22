package com.chembrovich.bsuir.topfilms.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.chembrovich.bsuir.topfilms.R;
import com.chembrovich.bsuir.topfilms.presenters.ElementsListPresenter;
import com.chembrovich.bsuir.topfilms.presenters.interfaces.IElementsListPresenter;
import com.chembrovich.bsuir.topfilms.views.interfaces.IElementsListView;


public class ElementsListFragment extends Fragment implements IElementsListView {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private IElementsListPresenter presenter;
    private int pageNumber;

    private OnFragmentInteractionListener mListener;

    public ElementsListFragment() {
        pageNumber = 1;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_elements_list, container, false);

        presenter = new ElementsListPresenter(this);
        presenter.makeRequestToGetTopRatedMovies(pageNumber);
        presenter.makeRequestToGetPhotos(pageNumber);

        recyclerView = view.findViewById(R.id.elements_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerViewAdapter = new RecyclerViewAdapter(this.getContext(), presenter);
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void updateList() {
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
