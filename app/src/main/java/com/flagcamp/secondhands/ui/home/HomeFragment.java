/*
I have problem with encapsulate the rendering code.
So if you want to use my rendering code to save time, the easiest way for now is to copy some codes,
OR YOU CAN TRY TO TEACH ME HOW TO ENCAPSULATE THEM.

Since I have so many views on homepage, I use [] to store adapters and managers.
I use StaggeredGridLayout to make a recyclerView HORIZONTALLY scrollable, change it to your own layout. eg LinearLayout
 */
package com.flagcamp.secondhands.ui.home;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.FragmentHomeBinding;
import com.flagcamp.secondhands.databinding.FragmentSearchBinding;
import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.repository.ProductRespository;
import com.flagcamp.secondhands.repository.ProductViewModelFactory;
import com.flagcamp.secondhands.ui.productDetail.ProductDetailFragment;
import com.flagcamp.secondhands.ui.search.SearchFragment;
import com.yuyakaido.android.cardstackview.Direction;


public class HomeFragment extends Fragment {
    private static int CATEGORIES = 7;

    private HomeViewModel viewModel;

    private FragmentHomeBinding binding;


    public HomeFragment(){
        // required empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstance){
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @NonNull Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        HomeProductAdapter[] productAdapters = new HomeProductAdapter[CATEGORIES];
        StaggeredGridLayoutManager[] layoutManagers = new StaggeredGridLayoutManager[CATEGORIES];
        RecyclerView[] recyclerViews = new RecyclerView[CATEGORIES];
        recyclerViews[0] = binding.productsResultsRecyclerView0;
        recyclerViews[1] = binding.productsResultsRecyclerView1;
        recyclerViews[2] = binding.productsResultsRecyclerView2;
        recyclerViews[3] = binding.productsResultsRecyclerView3;
        recyclerViews[4] = binding.productsResultsRecyclerView4;
        recyclerViews[5] = binding.productsResultsRecyclerView5;
        recyclerViews[6] = binding.productsResultsRecyclerView6;
        binding.homePageRecommendationBaby.setText("Baby & Kids");
        binding.homePageRecommendationCar.setText("Car");
        binding.homePageRecommendationClothing.setText("Clothing");
        binding.homePageRecommendationElectronic.setText("Electronics");
        binding.homePageRecommendationFurniture.setText("Furniture");
        binding.homePageRecommendationHealth.setText("Health");
        binding.homePageRecommendationHousehold.setText("Household");
        binding.homePageRecommendationTitle.setText("You Might Like These Products");
        binding.mapButton.setImageResource(R.drawable.baseline_explore_black_18dp);
//        binding.mapButton.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_navigation_map, null));
        binding.mapButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_navigation_home_to_navigation_map);
            }
        });
        for(int i = 0; i < CATEGORIES; i++){
            productAdapters[i] = new HomeProductAdapter();
            layoutManagers[i] = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
            recyclerViews[i].setAdapter(productAdapters[i]);
            recyclerViews[i].setLayoutManager(layoutManagers[i]);
        }
        for(int i = 0; i < CATEGORIES; i++){
            productAdapters[i].setItemCallback(new HomeProductAdapter.ItemCallback() {
                @Override
                public void onOpenDetails(Product product) {
                    // TODO
                    /*
                    HomeFragmentDirections.ActionNavigationHomeToNavigationDetail direction = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(product);
                    NavHostFragment.findNavController(HomeFragment.this).navigate(direction);

                    */
                    HomeFragmentDirections.ActionNavigationHomeToNavigationDetail direction = HomeFragmentDirections.actionNavigationHomeToNavigationDetail(product);
                    NavHostFragment.findNavController(HomeFragment.this).navigate(direction);

                }

            });
        }

        ProductRespository respository = new ProductRespository();
        viewModel = new ViewModelProvider(this, new ProductViewModelFactory(respository)).get(HomeViewModel.class);
        viewModel.setSearchInput("q");
        viewModel.getProducts().observe(getViewLifecycleOwner(),productResponse -> {
            if(productResponse != null){
                for(int i = 0; i < CATEGORIES; i++){
                    productAdapters[i].setProducts(productResponse.getCat(i));
                }
            }


        });

    }

}