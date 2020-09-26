package com.flagcamp.secondhands.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import com.flagcamp.secondhands.model.ProductResponse;
import com.flagcamp.secondhands.model.SearchResponse;
import com.flagcamp.secondhands.repository.ProductRepository;

public class SearchViewModel extends ViewModel {
    private final ProductRepository repository;
    private final MutableLiveData<String> searchInput = new MutableLiveData<>();
    private final MutableLiveData<String> categoryInput = new MutableLiveData<>("");
    private final MutableLiveData<String> locationInput = new MutableLiveData<>("");

    public SearchViewModel(ProductRepository repository) {
        this.repository = repository;
    }

    public void setSearchInput(String query) {
        searchInput.setValue(query);
    }
    public void setCategoryInput(String query) {
        query = query.equals("All Categories") ? "N/A" : query;
        categoryInput.setValue(query);
    }
    public void setLocationInput(String query) {
        query = query.equals("All States") ? "N/A" : query;
        locationInput.setValue(query);
    }

    public LiveData<SearchResponse> searchProducts() {
        CustomLiveData input = new CustomLiveData(searchInput, categoryInput, locationInput);
        return Transformations.switchMap(input, repository::searchProducts);
    }

    private static class CustomLiveData extends MediatorLiveData<Cell> {
        public CustomLiveData(MutableLiveData<String> searchInput,
                              MutableLiveData<String> categoryInput,
                              MutableLiveData<String> locationInput) {
            addSource(searchInput, new Observer<String>() {
                @Override
                public void onChanged(String s) {
                    setValue(new Cell(s, categoryInput.getValue(), locationInput.getValue()));
                }
            });
        }
    }

    public static class Cell {
        public String searchInput;
        public String categoryInput;
        public String locationInput;

        public Cell(String searchInput, String categoryInput, String locationInput) {
            this.searchInput = searchInput;
            this.categoryInput = categoryInput;
            this.locationInput = locationInput;
        }
    }
}
