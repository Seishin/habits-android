package co.naughtyspirit.habits.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import co.naughtyspirit.habits.views.fragments.LoginFragment;
import co.naughtyspirit.habits.views.fragments.RegisterFragment;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/26/15.
 * *
 * * NaughtySpirit 2015
 */
public class MainScreenFragmentsAdapter  extends FragmentStatePagerAdapter {
    private static final String TAG = MainScreenFragmentsAdapter.class.getName();
    
    public MainScreenFragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new LoginFragment();
        } else {
            return new RegisterFragment();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}