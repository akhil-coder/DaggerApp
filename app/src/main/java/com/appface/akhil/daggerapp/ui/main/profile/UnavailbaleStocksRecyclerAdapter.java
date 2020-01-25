package com.appface.akhil.daggerapp.ui.main.profile;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appface.akhil.daggerapp.R;
import com.appface.akhil.daggerapp.model.Stock;
import com.appface.akhil.daggerapp.model.StockUnavailable;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class UnavailbaleStocksRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<StockUnavailable> posts = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_list_item, parent, false);
        return new PostViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder)holder).bind(posts.get(position));
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public void setPosts(List<StockUnavailable> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        TextView title;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
        }

        public void bind(StockUnavailable stock){
            long barcode = stock.getBarcode();
            title.setText(String.valueOf(stock.getBarcode()));
        }
    }
}