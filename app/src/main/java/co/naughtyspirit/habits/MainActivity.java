package co.naughtyspirit.habits;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.squareup.otto.Subscribe;

import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.CreateUserEvent;
import co.naughtyspirit.habits.utils.Constants;
import co.naughtyspirit.habits.views.fragments.LoginFragment;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/22/15.
 * 
 * NaughtySpirit 2015
 */
public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        initUI();
    }

    private void initUI() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new LoginFragment(), Constants.TAG_FRAGMENT_LOGIN)
                .setCustomAnimations(R.anim.alpha_in, R.anim.slide_out_left)
                .commit();
    }

    @Subscribe
    public void onUserCreated(CreateUserEvent event) {
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
}
