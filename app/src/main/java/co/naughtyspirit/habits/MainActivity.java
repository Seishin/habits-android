package co.naughtyspirit.habits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.squareup.otto.Subscribe;

import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.GetUserStatsEvent;
import co.naughtyspirit.habits.bus.producers.UserEventsProducer;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/27/15.
 * *
 * * NaughtySpirit 2015
 */
public class MainActivity extends ActionBarActivity implements OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    
    private Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        UserEventsProducer.produceGetUserStatsEvent(this);
        
        initUI();
    }

    private void initUI() {
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                AuthProviderFactory.getProvider(this).logout();
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
    
    @Subscribe
    public void onUserStatsObtained(GetUserStatsEvent event) {
        Log.e(TAG, event.getStats().toString());
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
