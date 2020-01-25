package com.appface.akhil.daggerapp.ui.main.posts.subcategory;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appface.akhil.daggerapp.R;
import com.appface.akhil.daggerapp.model.Stock;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class SubCategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Stock> posts = new ArrayList<>();

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_post_list_item, parent, false);
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

    public void setPosts(List<Stock> posts){
        this.posts = posts;
        notifyDataSetChanged();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder{

        TextView  productDetails, stockQty;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);
            productDetails = itemView.findViewById(R.id.tv_productdetails);
            stockQty = itemView.findViewById(R.id.tv_stockqty);
        }

        public void bind(Stock stock){
            productDetails.setText(stock.getProductDetails());
            stockQty.setText(String.valueOf(stock.getStockQty()));
        }
    }
}