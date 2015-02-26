package co.naughtyspirit.habits;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.squareup.otto.Subscribe;

import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.CreateUserEvent;
import co.naughtyspirit.habits.bus.events.LoginUserEvent;
import co.naughtyspirit.habits.views.adapters.MainScreenFragmentsAdapter;
import co.naughtyspirit.habits.views.interfaces.OnViewPagerFragmentChange;
import co.naughtyspirit.habits.views.transforms.ZoomOutPageTransformer;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/22/15.
 * 
 * NaughtySpirit 2015
 */
public class MainActivity extends FragmentActivity implements OnViewPagerFragmentChange {

    private static final String TAG = MainActivity.class.getName();
    private ViewPager viewPager;
    private MainScreenFragmentsAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initUI();
    }

    private void initUI() {
        viewPagerAdapter = new MainScreenFragmentsAdapter(getSupportFragmentManager());
        
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Subscribe
    public void onUserCreated(CreateUserEvent event) {
        Log.e(TAG, event.getUser().toString());
    }
    
    @Subscribe
    public void onUserLoggedIn(LoginUserEvent event) {
        Log.e(TAG, event.getUser().toString());
    }

    @Override
    protected void onResume() {
        super.onResume();

        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        
        BusProvider.getInstance().unregister(this);
    }

    @Override
    public void setFragmentAt(int position) {
        viewPager.setCurrentItem(position);
    }
}
