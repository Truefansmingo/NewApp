package com.flagcamp.secondhands.ui.fav;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.data.Product;
import com.flagcamp.secondhands.databinding.FavProductBinding;

import java.util.ArrayList;
import java.util.List;

public class FavProductAdapter extends RecyclerView.Adapter<FavProductAdapter.FavProductViewHolder> {
    private List<Product> favList = new ArrayList<>();

    public void setFavList(List<Product> list){
        favList.clear();
        favList.addAll(favList);
        notifyDataSetChanged();
    }

    interface ItemCallback{
        void onOpenDetail(Product product);
        void onRemoveFav(Product product);
    }

    private ItemCallback itemCallback;


    public void setItemCallback(ItemCallback itemCallback){
        this.itemCallback = itemCallback;
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
        holder.productLocation.setText(product.postedAt);
        holder.productStatus.setText(product.status);
        //check image is valid
        if(product.urlToImage == null){
            holder.productPhoto.setImageResource(R.drawable.ic_wrong_photo_18dp);
        }else{
            holder.productPhoto.setImageResource(R.drawable.ic_correct_18dp);
        }
        //remove the fav item
        holder.favIcon.setOnClickListener(v -> itemCallback.onRemoveFav(product));
        //click the details
        holder.itemView.setOnClickListener(v -> itemCallback.onOpenDetail(product));
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
            productName = binding.savedFavProductTitle;
            productPrice = binding.savedFavProductPrice;
            productLocation = binding.savedFavProductLocation;
            productStatus = binding.savedFavProductStatus;
            favIcon = binding.savedFavProductFavIcon;
            productPhoto = binding.savedFavProductImageView;

        }
    }

}
