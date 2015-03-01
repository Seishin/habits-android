package co.naughtyspirit.habits.views.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import co.naughtyspirit.habits.views.fragments.DailyTasksFragment;
import co.naughtyspirit.habits.views.fragments.HabitsFragment;
import co.naughtyspirit.habits.views.fragments.LoginFragment;
import co.naughtyspirit.habits.views.fragments.RegisterFragment;
import co.naughtyspirit.habits.views.fragments.RewardsFragment;
import co.naughtyspirit.habits.views.fragments.ToDoTasksFragment;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/26/15.
 * *
 * * NaughtySpirit 2015
 */
public class MainScreenFragmentsAdapter extends FragmentStatePagerAdapter {
    private static final String TAG = MainScreenFragmentsAdapter.class.getName();

    public MainScreenFragmentsAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new HabitsFragment();  
            
            case 1:
                return new DailyTasksFragment();
            
            case 2:
                return new ToDoTasksFragment();
            
            case 3:
                return new RewardsFragment();
            
            default:
                return new HabitsFragment();
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}