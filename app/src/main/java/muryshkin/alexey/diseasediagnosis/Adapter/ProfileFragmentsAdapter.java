package muryshkin.alexey.diseasediagnosis.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Alexey Muryshkin on 27.11.2016.
 */

public class ProfileFragmentsAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments;

    public ProfileFragmentsAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0: return "Profile";
            case 1: return "Sessions";
            case 2: return "Settings";
            default: return null;
        }
    }
}
