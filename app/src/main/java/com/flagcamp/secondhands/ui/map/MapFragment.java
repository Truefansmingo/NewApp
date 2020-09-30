package com.flagcamp.secondhands.ui.map;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flagcamp.secondhands.R;
import com.flagcamp.secondhands.databinding.FragmentFavBinding;
import com.flagcamp.secondhands.databinding.FragmentMapBinding;
import com.flagcamp.secondhands.model.DummyData;
import com.flagcamp.secondhands.model.Product;
import com.flagcamp.secondhands.repository.ProductRepository;
import com.flagcamp.secondhands.repository.ProductViewModelFactory;
import com.flagcamp.secondhands.ui.fav.FavFragment;
import com.flagcamp.secondhands.ui.fav.FavFragmentDirections;
import com.flagcamp.secondhands.ui.fav.FavViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static androidx.core.content.ContextCompat.getSystemService;

public class MapFragment extends Fragment implements
        GoogleMap.OnInfoWindowClickListener,
        GoogleMap.OnCameraIdleListener{

    private MapViewModel mapViewModel;
    private GoogleMap map;
    private Map<Marker, Product> markerProductMap = new HashMap<>();

    private double longitude;
    private double latitude;
    private double curLon;
    private double curLat;
    SupportMapFragment mapFragment;
    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;
            map.getUiSettings().setZoomControlsEnabled(true);
            map.clear();

            //add marker
            addMarkersToMap(map,latitude,longitude);
            //move camera to the current location
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latitude,longitude),10));

            //set customize window adapter
            map.setInfoWindowAdapter(new CustomInfoWindowAdapter());
            //set click function jump to product detail
            map.setOnInfoWindowClickListener(MapFragment.this::onInfoWindowClick);
            // set camera move listener
            map.setOnCameraIdleListener(MapFragment.this::onCameraIdle);

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_map, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initLocation();
        mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null ) {
            mapFragment.getMapAsync(callback);
        }

        ImageButton myLocationButton = (ImageButton)getView().findViewById(R.id.myMapLocationButton);
        myLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                //add marker
                addMarkersToMap(map,curLat,curLon);
                //move camera to the current location
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(curLat,curLon), 10));
            }
        });

    }

    //TODO:update the method of call image url
    private void addMarkersToMap(GoogleMap map, double latitude, double longitude) {
        ProductRepository repository = new ProductRepository(getContext());
        mapViewModel = new ViewModelProvider(this, new ProductViewModelFactory(repository)).get(MapViewModel.class);
        List<Product> list = mapViewModel.getProductGeoInfo(latitude,longitude).getValue();
        if(list.size() == 0){
            Toast.makeText(
                    getContext(),
                    "No Products near this location",
                    Toast.LENGTH_SHORT).show();
            return;
        }
//        Toast.makeText(
//                getContext(),
//                "the number of marker:" + list.size(),
//                Toast.LENGTH_SHORT).show();

        for (Product product : list) {
            final LatLng location = new LatLng(product.lat, product.lon);
            //customize the marker
            IconGenerator iconGenerator = new IconGenerator(getContext());
            iconGenerator.setColor(Color.WHITE);
            iconGenerator.setTextAppearance(R.style.Theme_AppCompat_DayNight_DarkActionBar);
            Bitmap bm = iconGenerator.makeIcon(product.title);
            MarkerOptions markerOptions = new MarkerOptions()
                    .position(location)
                    .title(product.title)
                    .snippet(product.price)
                    .icon(BitmapDescriptorFactory.fromBitmap(bm));
            Marker marker = map.addMarker(markerOptions);
            markerProductMap.put(marker, product);

        }

    }


    @Override
    public void onInfoWindowClick(Marker marker) {
        Product product = markerProductMap.get(marker);
        MapFragmentDirections.ActionNavigationMapToNavigationDetails direction = MapFragmentDirections.actionNavigationMapToNavigationDetails(product);
        NavHostFragment.findNavController(MapFragment.this).navigate(direction);

    }
    //get the lan and lon of camera position
    //can refresh new marker if location out of scope
    @Override
    public void onCameraIdle() {
        CameraPosition cameraPosition = map.getCameraPosition();
        double tempLon = cameraPosition.target.longitude;
        double tempLat = cameraPosition.target.latitude;
        double minMoveLon = longitude-0.1;
        double maxMoveLon = longitude+0.1;
        double minMoveLat = latitude-0.1;
        double maxMoveLat = latitude+0.1;
        if(tempLon >maxMoveLon || tempLon < minMoveLon || tempLat > maxMoveLat || tempLat < minMoveLat){
//            Toast.makeText(
//                    getContext(),
//                    "trig move the camera",
//                    Toast.LENGTH_SHORT).show();
            longitude = tempLon;
            latitude = tempLat;
            mapFragment.getMapAsync(callback);
        }

    }


    class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {
        @Override
        public View getInfoWindow(Marker marker) {
            View view = getLayoutInflater().inflate(R.layout.map_info_windows, null);
            int badge = R.drawable.ic_correct_18dp;
            //((ImageView) view.findViewById(R.id.badge)).setImageURI();
            ((ImageView) view.findViewById(R.id.badge)).setImageResource(badge);
            String title = marker.getTitle();
            TextView titleUi = ((TextView) view.findViewById(R.id.title));
            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                SpannableString titleText = new SpannableString(title);
                titleText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, titleText.length(), 0);
                titleUi.setText(titleText);
            } else {
                titleUi.setText("");
            }
            String snippet = marker.getSnippet();
            TextView snippetUi = ((TextView) view.findViewById(R.id.snippet));
            if (snippet != null) {
                snippetUi.setText("Price: " + snippet);
            } else {
                snippetUi.setText("Price: n/a");
            }
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
    //already check permission in the main activity

    @SuppressLint("MissingPermission")
    private void initLocation() {
        //permission deny, give a default location
        if (Build.VERSION.SDK_INT >= 23
                && getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            this.latitude = 34.0522342;
            this.longitude = -118.2436849;
            Toast.makeText(
                    getContext(),
                    "Location permission is not granted, default to Los Angeles",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        LocationManager lm =
                (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
        //here must use list<string> in case one provider is null
        List<String> providers = lm.getProviders(true);
        Location location = null;
        for (String provider : providers) {
            Location l = lm.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (location == null || l.getAccuracy() < location.getAccuracy()) {
                // Found best last known location: %s", l);
                location = l;
            }
        }
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        curLat = location.getLatitude();
        curLon =location.getLongitude();
    }




}
