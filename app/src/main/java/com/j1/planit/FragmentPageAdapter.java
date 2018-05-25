package com.j1.planit;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by PC on 2018-05-01.
 */

public class FragmentPageAdapter extends FragmentPagerAdapter {


    public FragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new Page1Fragment();
                break;
            case 1:
                fragment = new Page2Fragment();
                break;
            case 2:
                fragment = new Page3Fragment();
                break;
            case 3:
                fragment = new Page4Fragment();
                break;

            case 4:
                fragment = new Page5Fragment();
                break;
        }
        return fragment;

    }

    @Override
    public int getCount() {
        return 5;
    }


}

