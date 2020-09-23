package com.flagcamp.secondhands.ui.productDetail;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
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
import com.flagcamp.secondhands.model.User;
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
        binding.productDetailFavorite.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                product.favorite = product.favorite == false ? true : false;
                if(product.favorite == true){
                    binding.productDetailFavorite.setImageResource(R.drawable.ic_favorite_24dp);
                }else{
                    binding.productDetailFavorite.setImageResource(R.drawable.ic_favorite_border_24dp);
                }
            }
        });
        if(product.favorite == true){
            binding.productDetailFavorite.setImageResource(R.drawable.ic_favorite_24dp);
        }else{
            binding.productDetailFavorite.setImageResource(R.drawable.ic_favorite_border_24dp);
        }

        //binding.productDetailChatButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigation_detail_to_navigation_profile, null));
        binding.productDetailChatButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String url = "https://firebasestorage.googleapis.com/v0/b/androidsecondhandmarket.appspot.com/o/testImgs_v1%2FTrump1.jpg?alt=media&token=a7da5dad-2354-4979-b155-75fb1e9e845d";
                User user = new User("0", "Rick Sun", url, "email", "5.0");
                ProductDetailFragmentDirections.ActionNavigationDetailToNavigationProfile action = ProductDetailFragmentDirections.actionNavigationDetailToNavigationProfile(user);
                Navigation.findNavController(view).navigate(action);

            }
        });
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