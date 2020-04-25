package com.ashokcouhan.blooduser;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.ashokcouhan.blooduser.fragments.DonateFragment;
import com.ashokcouhan.blooduser.fragments.HomeFragment;
import com.ashokcouhan.blooduser.fragments.ProfileFragment;
import com.ashokcouhan.blooduser.fragments.SearchFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HomeFragment();
            case 1:
                return new SearchFragment();
            case 2:
                return new DonateFragment();
            case 3:
                return new ProfileFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}