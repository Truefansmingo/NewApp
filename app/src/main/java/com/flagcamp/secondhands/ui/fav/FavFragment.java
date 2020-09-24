package com.flagcamp.secondhands.ui.fav;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flagcamp.secondhands.model.DummyData;
import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.repository.ProductRepository;
import com.flagcamp.secondhands.repository.ProductViewModelFactory;



import com.flagcamp.secondhands.databinding.FragmentFavBinding;

import java.util.List;


public class FavFragment extends Fragment {

    private FragmentFavBinding binding;
    private FavViewModel viewModel;
    private int id = 0;
    private  final String tag = FavFragment.this.getClass().getSimpleName();

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
        favProductAdapter.setItemCallBack(new FavProductAdapter.ItemCallBack() {
            @Override
            public void onOpenDetail(Product product) {
                Log.d("onOpenProduct", product.toString());
                FavFragmentDirections.ActionNavigationFavToNavigationDetails direction = FavFragmentDirections.actionNavigationFavToNavigationDetails(product);
                NavHostFragment.findNavController(FavFragment.this).navigate(direction);
            }

            @Override
            public void onRemoveFav(Product product) {
                Log.d("onDeleteFav", product.toString());
                viewModel.deleteFavProduct(id, product);
                //re-render fav list
                favProductAdapter.updateFavList(viewModel.getFavProductList(id).getValue());
            }
        });

        binding.favListsRecyclerView.setAdapter(favProductAdapter);
        binding.favListsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        ProductRepository repository = new ProductRepository();
        viewModel = new ViewModelProvider(this, new ProductViewModelFactory(repository)).get(FavViewModel.class);
        viewModel
                .getFavProductList(id)  //return variable is favProducts
                .observe(
                        getViewLifecycleOwner(),
                        favProducts-> {
                            if (favProducts != null) {
                                Log.d(tag, favProducts.toString());
                                favProductAdapter.updateFavList(favProducts);
                            }else {
                                Log.d(tag, "please add your first favoriabled produts");
                            }
                        });

    }

//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        viewModel.onCancel();
//    }

}
