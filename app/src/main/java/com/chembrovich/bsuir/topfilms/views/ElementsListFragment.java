package com.chembrovich.bsuir.topfilms.views;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import com.chembrovich.bsuir.topfilms.views.interfaces.IOnLoadMoreListener;
import com.chembrovich.bsuir.topfilms.views.interfaces.IOnRecycleViewItemClickListener;


public class ElementsListFragment extends Fragment implements IElementsListView {
    private RecyclerView recyclerView;
    private RecyclerViewAdapter recyclerViewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private IElementsListPresenter presenter;
    private int pageNumber;

    private OnElementsListFragmentInteractionListener interactionListener;

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
        layoutManager = new LinearLayoutManager(this.getContext());
        recyclerView.setLayoutManager(layoutManager);
        IOnRecycleViewItemClickListener onItemClick = new IOnRecycleViewItemClickListener() {
            @Override
            public void onItemSelected(int position) {
                interactionListener.onRecycleViewItemClick(presenter.getMovieIdByPosition(position)
                        ,presenter.getPhotoSrcByPosition(position)
                        ,presenter.getPhotoUserNameByPosition(position));
            }
        };
        recyclerViewAdapter = new RecyclerViewAdapter(this.getContext(), presenter, onItemClick);
        recyclerView.setAdapter(recyclerViewAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int visibleThreshold = 5;
            private int lastVisibleItem, totalItemCount;
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!recyclerViewAdapter.isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (recyclerViewAdapter.onLoadMoreListener != null) {
                        recyclerViewAdapter.onLoadMoreListener.onLoadMore();
                    }
                    recyclerViewAdapter.isLoading = true;
                }
            }
        });

        recyclerViewAdapter.setOnLoadMoreListener(new IOnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (presenter.getMoviesList().size() <= presenter.getMaxPageCount()) {
                    presenter.getMoviesList().add(null);
                    recyclerViewAdapter.notifyItemInserted(presenter.getMoviesList().size() - 1);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            presenter.getMoviesList().remove(presenter.getMoviesList().size() - 1);
                            recyclerViewAdapter.notifyItemRemoved(presenter.getMoviesList().size());
                            presenter.makeRequestToLoadMoreData();
                        }
                    }, 5000);
                } else {
                    Toast.makeText(getContext(), "Loading data completed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnElementsListFragmentInteractionListener) {
            interactionListener = (OnElementsListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnElementsListFragmentInteractionListener");
        }
    }

    @Override
    public void updateList() {
        recyclerViewAdapter.notifyDataSetChanged();
        recyclerViewAdapter.setLoaded();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        interactionListener = null;
    }

    public interface OnElementsListFragmentInteractionListener {
        void onRecycleViewItemClick(int movieId, String photoSrc,String photosUserName);
    }
}
