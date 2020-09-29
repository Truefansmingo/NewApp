package com.flagcamp.secondhands;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.flagcamp.secondhands.navigation.KeepStateNavigator;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private NavController navController;
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
    }

     @Override
     public boolean onSupportNavigateUp() {
            return navController.navigateUp();
     }

     //Change each fragment Action Bar Title; we can delete it if Action bar is no longer needed;
     public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
     }
}


