package com.voltiosx.wavescore.viewpagers;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.voltiosx.wavescore.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerTournamentAdapter extends androidx.fragment.app.FragmentStatePagerAdapter { // deshuso FragmentPagerAdapter

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_tournament_start, R.string.tab_tournament_form}; //R.string.tab_tournament_end
    private final Context mContext;

    public SectionsPagerTournamentAdapter(Context context, FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderTournamentFragment (defined as a static inner class below).
        return PlaceholderTournamentFragment.newInstance(position + 1);
    }

    @Nullable
    /*@Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }*/

    @Override
    public int getCount() {
        // Total de paginas a mostrar.
        return 2;
    }
}