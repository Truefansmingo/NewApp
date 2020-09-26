package com.flagcamp.secondhands.ui.search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.FragmentSearchBinding;
import com.flagcamp.secondhands.databinding.SearchProductItemBinding;
import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.repository.ProductRepository;
import com.flagcamp.secondhands.repository.ProductViewModelFactory;

public class SearchFragment extends Fragment {

    private FragmentSearchBinding binding;
    private SearchViewModel viewModel;
    private SearchAdapter searchAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchAdapter = new SearchAdapter();
        binding.resultsRecyclerView.setAdapter(searchAdapter);
        binding.resultsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        setUpSearch();
        setUpCategory();
        setUpLocation();

        // switch fragment
        searchAdapter.setItemCallback(new SearchAdapter.ItemCallback() {
            @Override
            public void onOpenDetails(Product product) {
                SearchFragmentDirections.ActionNavigationSearchToNavigationDetail direction = SearchFragmentDirections.actionNavigationSearchToNavigationDetail(product);
                NavHostFragment.findNavController(SearchFragment.this).navigate(direction);
            }
        });

    }

    private void setUpSearch() {
        binding.searchTextView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (!query.isEmpty()) {
                    viewModel.setSearchInput(query);
                }
                binding.searchTextView.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        ProductRepository repository = new ProductRepository(requireContext());
        viewModel = new ViewModelProvider(this, new ProductViewModelFactory(repository))
                .get(SearchViewModel.class);
        viewModel.searchProducts()
                .observe(
                        getViewLifecycleOwner(),
                        productResponse -> {
                            if (productResponse != null) {
                                searchAdapter.setProducts(productResponse.products);
                            } else {
                                Log.d("search: ", "no product returned");
                            }
                        }
                );
    }

    private void setUpCategory() {
        ArrayAdapter<CharSequence> categorySpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.category, android.R.layout.simple_spinner_item);
        categorySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.searchCategorySpinner.setAdapter(categorySpinnerAdapter);
        binding.searchCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setCategoryInput(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setUpLocation() {
        ArrayAdapter<CharSequence> locationSpinnerAdapter = ArrayAdapter.createFromResource(getContext(), R.array.location, android.R.layout.simple_spinner_item);
        locationSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.searchLocationSpinner.setAdapter(locationSpinnerAdapter);
        binding.searchLocationSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                viewModel.setLocationInput(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}