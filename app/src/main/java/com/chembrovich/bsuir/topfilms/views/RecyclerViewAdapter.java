package com.chembrovich.bsuir.topfilms.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.chembrovich.bsuir.topfilms.presenters.interfaces.IElementsListPresenter;
import com.chembrovich.bsuir.topfilms.R;
import com.squareup.picasso.Picasso;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private IElementsListPresenter presenter;
    private Context context;


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView rowNumberTextView;
        private ImageView rowImageView;
        private TextView rowTitleTextView;

        public MyViewHolder(View itemView) {
            super(itemView);
            rowNumberTextView = itemView.findViewById(R.id.row_number_text_view);
            rowImageView = itemView.findViewById(R.id.row_image_view);
            rowTitleTextView = itemView.findViewById(R.id.row_title_text_view);
        }
    }

    RecyclerViewAdapter(Context context, IElementsListPresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.rowTitleTextView.setText(presenter.getMoviesList().get(position).getTitle());
        holder.rowNumberTextView.setText(context.getString(R.string.list_item_number, position + 1));
        if (!presenter.getPhotosList().isEmpty()) {
            Picasso.with(context)
                    .load(presenter.getPhotosList().get(position).getUrls().getThumb())
                    .into(holder.rowImageView);
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return presenter.getMoviesList().size();
    }
}
