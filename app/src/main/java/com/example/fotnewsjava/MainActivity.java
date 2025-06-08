package com.example.fotnewsjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.fotnewsjava.fragments.AcademicFragment;
import com.example.fotnewsjava.fragments.DevInfoFragment;
import com.example.fotnewsjava.fragments.EventsFragment;
import com.example.fotnewsjava.fragments.ProfileFragment;
import com.example.fotnewsjava.fragments.SportsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private Fragment sportsFragment = new SportsFragment();
    private Fragment academicFragment = new AcademicFragment();
    private Fragment eventsFragment = new EventsFragment();
    private Fragment profileFragment = new ProfileFragment();
    private Fragment devInfoFragment = new DevInfoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_sports) {
                loadFragment(sportsFragment);
                return true;
            } else if (id == R.id.nav_academic) {
                loadFragment(academicFragment);
                return true;
            } else if (id == R.id.nav_events) {
                loadFragment(eventsFragment);
                return true;
            } else if (id == R.id.nav_profile) {
                loadFragment(profileFragment);
                return true;
            } else if (id == R.id.nav_dev_info) {
                loadFragment(devInfoFragment);
                return true;
            }
            return false;
        });


        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.nav_sports);
        }
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
