package com.flagcamp.secondhands.ui.fav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.FavProductBinding;
import com.flagcamp.secondhands.model.Product;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.List;

public class FavProductAdapter extends RecyclerView.Adapter<FavProductAdapter.FavProductViewHolder> {
    private List<Product> favList = new ArrayList<>();
    private ItemCallBack itemCallBack;

    public void updateFavList(List<Product> list){
        favList.clear();
        favList.addAll(list);
        notifyDataSetChanged();
    }

    interface ItemCallBack{
        void onOpenDetail(Product product);
        void onRemoveFav(Product product);
    }

    public void setItemCallBack(ItemCallBack itemCallBack){
        this.itemCallBack = itemCallBack;
    }


    @NonNull
    @Override
    public FavProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fav_product,parent,false);
        return new FavProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavProductViewHolder holder, int position) {
        Product product = favList.get(position);
        holder.productName.setText(product.title);
        holder.productPrice.setText(product.price);
        holder.productLocation.setText(product.location);
        if(product.status){
            holder.productStatus.setText("Available");
        }else{
            holder.productStatus.setText("Soldout");
        }

        //check image is valid
        if(product.urlToImage == null){
            holder.productPhoto.setImageResource(R.drawable.ic_wrong_photo_18dp);
        }else{
           Picasso.get().load(product.urlToImage.get(0)).into(holder.productPhoto);
        }
        holder.favIcon.setOnClickListener(v-> itemCallBack.onRemoveFav(product));
        holder.itemView.setOnClickListener(v -> itemCallBack.onOpenDetail(product));
    }

    @Override
    public int getItemCount() {
        return favList.size();
    }


    public static class FavProductViewHolder extends RecyclerView.ViewHolder{

        TextView productName;
        TextView productPrice;
        TextView productLocation;
        TextView productStatus;
        ImageView favIcon;
        ImageView productPhoto;

        public FavProductViewHolder(@NonNull View itemView) {
            super(itemView);

            FavProductBinding binding = FavProductBinding.bind(itemView);
            productName = binding.favProductTitleContent;
            productPrice = binding.favProductPriceContent;
            productLocation = binding.favProductLocationContent;
            productStatus = binding.favProductStatusContent;
            favIcon = binding.favProductFavIcon;
            productPhoto = binding.favProductImageView;

        }
    }

}
