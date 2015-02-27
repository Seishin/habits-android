package co.naughtyspirit.habits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.AuthFailureEvent;
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
public class AuthActivity extends FragmentActivity implements OnViewPagerFragmentChange {

    private static final String TAG = AuthActivity.class.getName();
    private ViewPager viewPager;
    private MainScreenFragmentsAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    
        if (AuthProviderFactory.getProvider(this).isUserLoggedIn()) {
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        
        initUI();
    }

    private void initUI() {
        viewPagerAdapter = new MainScreenFragmentsAdapter(getSupportFragmentManager());
        
        viewPager = (ViewPager) findViewById(R.id.pager);
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Subscribe
    public void onUserCreatedSuccess(CreateUserEvent event) {
        boolean success = AuthProviderFactory.getProvider(this).login(event.getUser());
        
        if (success) {
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
    
    @Subscribe
    public void onUserCreatedFail(AuthFailureEvent event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
    }
    
    @Subscribe
    public void onUserLoggedInSuccess(LoginUserEvent event) {
        boolean success = AuthProviderFactory.getProvider(this).login(event.getUser());

        if (success) {
            Intent intent = new Intent(AuthActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

    @Subscribe
    public void onUserLoggedInFail(AuthFailureEvent event) {
        Toast.makeText(this, event.getMessage(), Toast.LENGTH_SHORT).show();
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
