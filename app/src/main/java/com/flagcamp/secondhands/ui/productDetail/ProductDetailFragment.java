package com.flagcamp.secondhands.ui.productDetail;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.FragmentHomeBinding;
import com.flagcamp.secondhands.databinding.FragmentProductDetailBinding;
import com.flagcamp.secondhands.databinding.FragmentSearchBinding;
import com.flagcamp.secondhands.model.Image;
import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.repository.ProductRespository;
import com.flagcamp.secondhands.repository.ProductViewModelFactory;
import com.flagcamp.secondhands.ui.search.SearchFragment;
import com.squareup.picasso.Picasso;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class ProductDetailFragment extends Fragment {
    private static int CATAGORIES = 5;

    private FragmentProductDetailBinding binding;

    private ProductDetailViewModel viewModel;
    private StaggeredGridLayoutManager layoutManager;


    public ProductDetailFragment(){
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        // Inflate the layout for this fragment
        binding = FragmentProductDetailBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);


        Product product = ProductDetailFragmentArgs.fromBundle(getArguments()).getProduct();
        binding.productDetailPriceView.setText(product.price);
        binding.productDetailDescriptionView.setText(product.description);
        binding.productDetailSellerView.setText(product.seller);
        binding.productDetailTitleView.setText(product.title);
        binding.productDetailChatButton.setText("Chat With Me!");
        binding.productDetailRequestToBuyButton.setText("Request To Buy!");

        ProductDetailAdapter productAdapter = new ProductDetailAdapter();
        layoutManager = new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL);

        binding.productDetailImageRecyclerView.setLayoutManager(layoutManager);
        binding.productDetailImageRecyclerView.setAdapter(productAdapter);

        List<Image> imageList = new ArrayList<>();
        for(String s : product.urlToImage){
            imageList.add(new Image(s));
        }



        ProductRespository respository = new ProductRespository();
        viewModel = new ViewModelProvider(this, new ProductViewModelFactory(respository)).get(ProductDetailViewModel.class);
        viewModel.setSearchInput("us");
        viewModel.getProducts().observe(getViewLifecycleOwner(),productResponse -> {
            if(productResponse != null){
                productAdapter.setImages(imageList);

            }


        });



    }

}