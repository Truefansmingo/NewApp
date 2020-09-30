package com.flagcamp.secondhands;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.flagcamp.secondhands.navigation.KeepStateNavigator;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity{
    private NavController navController;
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager()
                .findFragmentById(R.id.nav_host_fragment);
        navController = navHostFragment.getNavController();

        Navigator navigator =
                new KeepStateNavigator(this, navHostFragment.getChildFragmentManager(), R.id.nav_host_fragment);
        navController.getNavigatorProvider().addNavigator(navigator);

        navController.setGraph(R.navigation.nav_graph);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupActionBarWithNavController(this, navController);
        //request to the location
        requestLocationPermission();
    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    private void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            try {
                if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } // for 23 version and above we need to request at runtime.
    }


}


