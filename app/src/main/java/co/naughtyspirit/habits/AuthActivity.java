package co.naughtyspirit.habits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.users.AuthFailureEvent;
import co.naughtyspirit.habits.bus.events.users.CreateUserEvent;
import co.naughtyspirit.habits.bus.events.users.LoginUserEvent;
import co.naughtyspirit.habits.ui.adapters.AuthScreenFragmentsAdapter;
import co.naughtyspirit.habits.ui.interfaces.OnViewPagerFragmentChange;
import co.naughtyspirit.habits.ui.transforms.ZoomOutPageTransformer;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/22/15.
 * 
 * NaughtySpirit 2015
 */
public class AuthActivity extends FragmentActivity implements OnViewPagerFragmentChange {

    private static final String TAG = AuthActivity.class.getName();

    @InjectView(R.id.pager) ViewPager viewPager;

    private AuthScreenFragmentsAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.inject(this);
        AuthProviderFactory.onCreate(this);

        initUI();
    }

    private void initUI() {
        viewPagerAdapter = new AuthScreenFragmentsAdapter(getSupportFragmentManager());
        
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Subscribe
    public void onUserCreatedSuccess(CreateUserEvent event) {
        boolean success = AuthProviderFactory.getProvider().login(event.getUser());
        
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
        boolean success = AuthProviderFactory.getProvider().login(event.getUser());

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
