package com.example.diary_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class bottom_nav extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    home home = new home();
    diary diary = new diary();
    add_info add_info = new add_info();
    profile profile = new profile();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_nav);

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Set the default fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, home).commit();

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                int Itemid = item.getItemId();

                if(Itemid == R.id.nav_home){
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, home).commit();
                }else if(Itemid == R.id.nav_diary){
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, diary).commit();
                }else if(Itemid == R.id.nav_add_work){
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, add_info).commit();
                }else if(Itemid == R.id.nav_profile){
                    getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, profile).commit();
                }

                return true;
            }
        });
    }
}
