package com.example.sikandermangat.click.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.sikandermangat.click.Fragments.ChatFragment;
import com.example.sikandermangat.click.Fragments.EmptyFragment;
import com.example.sikandermangat.click.Fragments.StoryFragment;

public class MainPageAdapter extends FragmentPagerAdapter{


    public MainPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return ChatFragment.create();

            case 1:
                return EmptyFragment.create();

            case 2:
                return StoryFragment.create();

                 }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
