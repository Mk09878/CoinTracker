package com.example.ilovezappos;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.ilovezappos.fragments.Asks;
import com.example.ilovezappos.fragments.Bids;
import com.example.ilovezappos.fragments.Interval;
import com.example.ilovezappos.fragments.Transactions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView nav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nav = findViewById(R.id.bottomNavigationView);

        nav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Transactions()).commit();



    }

    // Based on the item clicked, replaces the current fragment
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selected = null;

            switch (menuItem.getItemId()){
                case R.id.transcations:
                    selected = new Transactions();
                    break;

                case R.id.ask:
                    selected = new Asks();
                    break;

                case R.id.bid:
                    selected = new Bids();
                    break;

                case R.id.interval:
                    selected = new Interval();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selected).commit();
            return true;
        }
    };
}
