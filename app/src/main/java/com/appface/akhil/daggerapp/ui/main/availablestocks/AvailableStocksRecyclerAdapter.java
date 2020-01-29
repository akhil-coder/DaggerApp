package com.appface.akhil.daggerapp.ui.main.availablestocks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appface.akhil.daggerapp.R;
import com.appface.akhil.daggerapp.model.Category;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

public class AvailableStocksRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Category> posts = new ArrayList<>();
    String brand;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_category_list_item, parent, false);
        PostViewHolder postViewHolder = new PostViewHolder(view);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((PostViewHolder) holder).bind(posts.get(position));
    }

    @Override
    public int getItemCount() {

        return posts.size();
    }

    public void setPosts(List<Category> posts) {
        this.posts = posts;
        notifyDataSetChanged();
    }


    public class PostViewHolder extends RecyclerView.ViewHolder {

        TextView title;

        public PostViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {

                        brand = posts.get(adapterPosition).getBrand();
//                      PostsFragmentsDirections.ActionStartChat actionStartChat = PostsFragmentsDirections.actionStartChat(brand);

                        Bundle bundle = new Bundle();
                        bundle.putString("brandname", brand);
                        Navigation.findNavController(v).navigate(R.id.subCategoryScreen, bundle);
                    }
                }
            });

            title = itemView.findViewById(R.id.title);
        }

            public void bind (Category stock){
                title.setText(stock.getBrand());
            }
        }
    }

