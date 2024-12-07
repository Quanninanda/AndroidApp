package com.example.androidtest.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.androidtest.ActivityFragment;
import com.example.androidtest.HomeFragment;
import com.example.androidtest.InfoFragment;
import com.example.androidtest.ProfileFragment;
import com.example.androidtest.StatFragment;


public class ViewPagerAdapter extends FragmentStateAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0){
            return new HomeFragment();
        } else if (position == 1) {
            return new ActivityFragment();
        } else if (position == 2) {
            return new StatFragment();
        } else if (position == 3) {
            return new InfoFragment();
        } else if (position == 4) {
            return new ProfileFragment();
        } else {
            return new HomeFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
