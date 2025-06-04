package com.laura.marvel;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.laura.marvel.fragments.ComicFragment;
import com.laura.marvel.fragments.HomeFragment;
import com.laura.marvel.fragments.SettingFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottom_nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.main_bg));
        setContentView(R.layout.activity_main);

        bottom_nav = findViewById(R.id.nav_options);
        loadFragment(new HomeFragment());

        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int itemId = item.getItemId();
                boolean state = false;

                if (itemId == R.id.item_home) {
                    fragment = new HomeFragment();
                } else if ( itemId == R.id.item_comic ) {
                    fragment = new ComicFragment();
                } else if ( itemId == R.id.item_settings) {
                    fragment = new SettingFragment();
                }

                if (fragment != null) {
                    loadFragment(fragment);
                    state = true;
                }

                return state;
            }
        });

    }

    public void loadFragment(Fragment selectedFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit();
    }
}