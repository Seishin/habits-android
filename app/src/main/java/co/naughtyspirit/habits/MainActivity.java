package co.naughtyspirit.habits;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.users.GetUserStatsEvent;
import co.naughtyspirit.habits.bus.producers.UserEventsProducer;
import co.naughtyspirit.habits.views.adapters.MainScreenFragmentsAdapter;
import co.naughtyspirit.habits.views.transforms.ZoomOutPageTransformer;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/27/15.
 * *
 * * NaughtySpirit 2015
 */
public class MainActivity extends FragmentActivity implements OnClickListener {

    private static final String TAG = MainActivity.class.getName();
    
    private Button logout;
    private TextView expValue;
    private ImageView expBarMask;
    private float viewWidth;
    private TextView hpValue;
    private ImageView hpBarMask;
    private ViewPager viewPager;
    private RelativeLayout.LayoutParams params;
    private View selectedView;
    private int displayWidth;
    private TextView level;
    private TextView nextLevel;
    private TextView gold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AuthProviderFactory.onCreate(this);
        
        UserEventsProducer.produceGetUserStatsEvent(this);
        
        initUI();
    }

    private void initUI() {
        displayWidth = getResources().getDisplayMetrics().widthPixels;
        
        gold = (TextView) findViewById(R.id.gold);
        
        viewPager = (ViewPager) findViewById(R.id.tasks_pager);
        viewPager.setAdapter(new MainScreenFragmentsAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
        
        logout = (Button) findViewById(R.id.logout);
        logout.setOnClickListener(this);

        level = (TextView) findViewById(R.id.level);
        nextLevel = (TextView) findViewById(R.id.next_lvl);
        
        expValue = (TextView) findViewById(R.id.exp_value);
        expBarMask = (ImageView) findViewById(R.id.exp_mask);
        
        hpValue = (TextView) findViewById(R.id.hp_value);
        hpBarMask = (ImageView) findViewById(R.id.hp_mask);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.logout:
                AuthProviderFactory.getProvider().logout();
                Intent intent = new Intent(MainActivity.this, AuthActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }
    
    @Subscribe
    public void onUserStatsObtained(GetUserStatsEvent event) {
        gold.setText(String.valueOf(event.getStats().getGold()));
        
        level.setText(String.valueOf(event.getStats().getLvl()));
        nextLevel.setText(String.valueOf(event.getStats().getLvl() + 1));

        expValue.setText(event.getStats().getExp() + " / " + event.getStats().getMaxLvlExp());
        expBarMask.getLayoutParams().width = getViewWidth(getExpBarWidth(), event.getStats().getExp(), event.getStats().getMinLvlExp(),
                event.getStats().getMaxLvlExp());

        hpValue.setText(event.getStats().getHp() + " / 100");
        hpBarMask.getLayoutParams().width = getViewWidth(displayWidth, event.getStats().getHp(), 0, 100);
    }
    
    private int getViewWidth(int width, int value, int minValue, int maxValue) {
        return (value - minValue) * width / (maxValue - minValue);
    }
    
    private int getExpBarWidth() {
        return displayWidth - (level.getWidth() + nextLevel.getWidth());
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
