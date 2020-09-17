package com.flagcamp.secondhands.ui.home;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.HomeProductItemBinding;
import com.flagcamp.secondhands.model.Product;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeProductAdapter extends RecyclerView.Adapter<HomeProductAdapter.HomeProductViewHolder> {
    interface ItemCallback {
        void onOpenDetails(Product product);
    }
    private ItemCallback itemCallback;

    public void setItemCallback(ItemCallback itemCallback) {
        this.itemCallback = itemCallback;
    }
    private List<Product> products = new ArrayList<>();

    public void setProducts(List<Product> productList){
        products.clear();;
        products.addAll(productList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_product_item, parent, false);
        return new HomeProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeProductViewHolder holder, int position){
        Product product = products.get(position);
        holder.favoriteImageView.setImageResource(R.drawable.ic_favorite_24dp);
        holder.itemTitleTextView.setText(product.title);
        holder.itemPriceView.setText(product.price);
        holder.itemSellerView.setText(product.seller);
        //holder.itemImageView.setImageResource(R.drawable.ic_save_black_24dp);
        Picasso.get().load(product.urlToImage.get(0)).into(holder.itemImageView);
        holder.itemImageView.setOnClickListener(v -> itemCallback.onOpenDetails(product));
        holder.itemImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                itemCallback.onOpenDetails(product);
            }
        });
    }

    @Override
    public int getItemCount(){
        return products.size();
    }


    public static class HomeProductViewHolder extends RecyclerView.ViewHolder{
        ImageView favoriteImageView;
        ImageView itemImageView;
        TextView itemTitleTextView;
        TextView itemSellerView;
        TextView itemPriceView;
        public HomeProductViewHolder(@NonNull View itemView){
            super(itemView);
            HomeProductItemBinding binding = HomeProductItemBinding.bind(itemView);
            favoriteImageView = binding.homeItemFavorite;
            itemImageView = binding.homeItemIamge;
            itemSellerView = binding.homeItemSeller;
            itemTitleTextView = binding.homeItemTitle;
            itemPriceView = binding.homeItemPrice;
        }
    }
}
