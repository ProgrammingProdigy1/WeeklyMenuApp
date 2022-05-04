package com.projects.Screen2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.projects.Screen1.MainActivity;
import com.projects.Screen1.R;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerLayout = navigationView.getHeaderView(0);
        TextView tv = headerLayout.findViewById(R.id.navUsername_tv);
        tv.setText(getIntent().getStringExtra("Username"));

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null) { // The content of the if statement sets the first fragment to be opened
            // when the HomeScreen activity loads to be the MyData fragment,
            // However, by using the if statement we don't display the myData fragment if the device was rotated, even though the
            // HomeScreen activity was reloaded.
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new MydataFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_myData);
        }
    }

    // makes the navigation drawer close as back button is pressed.
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_myData:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MydataFragment()).commit();
                item.setCheckable(true);
                break;
            case R.id.nav_food:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new FoodFragment()).commit();
                break;
            case R.id.nav_sport:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SportFragment()).commit();
                break;
            case R.id.nav_logout:
                Intent success = new Intent(this, MainActivity.class);
                startActivity(success);
                Toast.makeText(HomeScreen.this, "התנתקת מהמשתמש", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_exit:
                finish();
                finishAffinity();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }
}