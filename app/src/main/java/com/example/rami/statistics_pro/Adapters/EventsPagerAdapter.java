package com.example.rami.statistics_pro.Adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.rami.statistics_pro.Fragments.ChooseNumbersFragment;
import com.example.rami.statistics_pro.Fragments.PersonalAreaFragment;
import com.example.rami.statistics_pro.Fragments.StatisticsSearchFragment;
import com.example.rami.statistics_pro.Fragments.UpcomingEventsFragment;
import com.example.rami.statistics_pro.R;


public class EventsPagerAdapter extends FragmentPagerAdapter {
    private Context mContext;

    public EventsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChooseNumbersFragment();
            case 1:
                return new UpcomingEventsFragment();
            case 2:
                return new StatisticsSearchFragment();
            default:
                return new ChooseNumbersFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.choose_numbers_page_title);
            case 1:
                return mContext.getString(R.string.search_statistics_queries_page_title);
            case 2:
                return mContext.getString(R.string.settings_page_title);
            default:
                return mContext.getString(R.string.choose_numbers_page_title);
        }
    }
}
