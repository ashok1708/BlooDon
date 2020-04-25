package com.ashokcouhan.blooduser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.ashokcouhan.blooduser.fragments.*;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {


    BottomNavigationView bottomNavigation;
    Toolbar toolbar;
    ViewPager viewPager;
    private ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigation = findViewById(R.id.bottom_navigation);
       // viewPager=findViewById(R.id.viewPager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(mViewPagerAdapter);
        //toolbar=findViewById(R.id.toolbar);

        bottomNavigation.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        openFragment(HomeFragment.newInstance("", ""));


    }
    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                @Override public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_home:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                //toolbar.setTitle("Home");
                            }
                            openFragment(HomeFragment.newInstance("", ""));
                            return true;
                        case R.id.action_search:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                //toolbar.setTitle("Search");
                            }

                            openFragment(SearchFragment.newInstance("", ""));
                            return true;
                        case R.id.action_donate:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                //toolbar.setTitle("Donate");
                            }

                            openFragment(DonateFragment.newInstance("", ""));
                            return true;
                        case R.id.action_profile:
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                //toolbar.setTitle("Profile");
                            }
                            viewPager.setCurrentItem(3);
                            openFragment(ProfileFragment.newInstance("", ""));
                            return true;
                    }
                    return false;
                }
            };


    public void setNavigationVisibility(boolean visible) {
        if (bottomNavigation.isShown() && !visible) {

            bottomNavigation.animate().translationY(bottomNavigation.getHeight());
            //bottomNavigation.setVisibility(View.GONE);
        }
        else if (!bottomNavigation.isShown() && visible){
            bottomNavigation.animate().translationY(0);
            //bottomNavigation.setVisibility(View.VISIBLE);
        }
    }

    public void hideBottomNavigationView() {
        bottomNavigation.animate().translationY(bottomNavigation.getHeight());
    }

    public void showBottomNavigationView() {
        bottomNavigation.animate().translationY(0);
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(R.drawable.ic_exit_to_app_black_24dp).setTitle("Exit")
                .setMessage("Are You Sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No",null).show();

    }
}
