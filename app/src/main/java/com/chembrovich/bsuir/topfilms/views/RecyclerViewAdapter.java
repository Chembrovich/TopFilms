package com.chembrovich.bsuir.topfilms.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chembrovich.bsuir.topfilms.presenters.interfaces.IElementsListPresenter;
import com.chembrovich.bsuir.topfilms.R;
import com.chembrovich.bsuir.topfilms.views.interfaces.IOnLoadMoreListener;
import com.chembrovich.bsuir.topfilms.views.interfaces.IOnRecycleViewItemClickListener;
import com.squareup.picasso.Picasso;

public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public boolean isLoading;

    public IOnLoadMoreListener onLoadMoreListener;
    private IOnRecycleViewItemClickListener onItemClick;
    private IElementsListPresenter presenter;
    private Context context;


    public static class ItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView rowNumberTextView;
        private ImageView rowImageView;
        private TextView rowTitleTextView;
        private IOnRecycleViewItemClickListener clickListener;

        public ItemViewHolder(View itemView) {
            super(itemView);
            rowNumberTextView = itemView.findViewById(R.id.row_number_text_view);
            rowImageView = itemView.findViewById(R.id.row_image_view);
            rowTitleTextView = itemView.findViewById(R.id.row_title_text_view);
            itemView.setOnClickListener(this);
        }

        public void setClickListener(IOnRecycleViewItemClickListener clickListener) {
            this.clickListener = clickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemSelected(getAdapterPosition());
        }
    }

    public static class  LoadingItemViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public LoadingItemViewHolder(View loadingView) {
            super(loadingView);
            progressBar = loadingView.findViewById(R.id.progress_bar);
        }
    }

    RecyclerViewAdapter(Context context, IElementsListPresenter presenter,IOnRecycleViewItemClickListener onItemClick) {
        this.context = context;
        this.presenter = presenter;
        this.onItemClick = onItemClick;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            itemViewHolder.rowTitleTextView.setText(presenter.getMoviesList().get(position).getTitle());
            itemViewHolder.rowNumberTextView.setText(context.getString(R.string.list_item_number, position + 1));
            if (!presenter.getPhotosList().isEmpty()) {
                Picasso.with(context)
                        .load(presenter.getPhotosList().get(position).getUrls().getThumb())
                        .into(itemViewHolder.rowImageView);
            }
            ((ItemViewHolder) holder).setClickListener(new IOnRecycleViewItemClickListener() {
                @Override
                public void onItemSelected(int position) {
                    onItemClick.onItemSelected(position);
                }
            });
        }
        else if (holder instanceof LoadingItemViewHolder) {
            LoadingItemViewHolder loadingViewHolder = (LoadingItemViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_recycler_view_row, parent, false);
            return new ItemViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_loading, parent, false);
            return new LoadingItemViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position) {
        if (presenter.getMoviesList().get(position) == null) {
            return VIEW_TYPE_LOADING;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    public void setOnLoadMoreListener(IOnLoadMoreListener onLoadMoreListener) {
        this.onLoadMoreListener = onLoadMoreListener;
    }

    @Override
    public int getItemCount() {
        return presenter.getMoviesList().size();
    }

    public void setLoaded(){
        this.isLoading = false;
    }
}
