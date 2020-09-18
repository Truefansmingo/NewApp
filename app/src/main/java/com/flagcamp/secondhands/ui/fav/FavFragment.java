package com.flagcamp.secondhands.ui.fav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.data.DummyData;
import com.flagcamp.secondhands.data.Product;
import com.flagcamp.secondhands.data.ProductViewModelFactory;

import com.flagcamp.secondhands.databinding.FragmentFavBinding;


public class FavFragment extends Fragment {

    private FragmentFavBinding binding;
    private FavViewModel viewModel;
    private int id;

    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavBinding.inflate(inflater,container,false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FavProductAdapter favProductAdapter = new FavProductAdapter();
        favProductAdapter.setItemCallback(new FavProductAdapter.ItemCallback() {
            @Override
            public void onOpenDetail(Product product) {
                Log.d("onOpenProduct", product.toString());
//                FavFragmentDirections.ActionNavigationFavToNavigationDetails direction = FavFragmentDirections.actionNavigationFavToNavigationDetails(product);
//                NavHostFragment.findNavController(FavFragment.this).navigate(direction);
            }
            @Override
            public void onRemoveFav(Product product) {
                viewModel.deleteFavProduct(id, product);}
            });

        binding.favListsRecyclerView.setAdapter(favProductAdapter);
        binding.favListsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        DummyData data = new DummyData();
        viewModel = new ViewModelProvider(this, new ProductViewModelFactory(data)).get(FavViewModel.class);
        viewModel
                .getFavProductList(id)
                .observe(
                        getViewLifecycleOwner(),
                        savedProducts-> {
                            if (savedProducts != null) {
                                Log.d("SaveFragment", savedProducts.toString());
                                favProductAdapter.setFavList(savedProducts);
                            }
                        });

    }
}