package co.naughtyspirit.habits;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.users.GetUserStatsEvent;
import co.naughtyspirit.habits.bus.producers.UserEventsProducer;
import co.naughtyspirit.habits.ui.adapters.MainScreenFragmentsAdapter;
import co.naughtyspirit.habits.ui.transforms.ZoomOutPageTransformer;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/27/15.
 * *
 * * NaughtySpirit 2015
 */
public class MainActivity extends FragmentActivity {

    private static final String TAG = MainActivity.class.getName();
    
    @InjectView(R.id.exp_value) TextView expValue;
    @InjectView(R.id.exp_mask) ImageView expBarMask;
    @InjectView(R.id.hp_value) TextView hpValue;
    @InjectView(R.id.hp_mask) ImageView hpBarMask;
    @InjectView(R.id.tasks_pager) ViewPager viewPager;
    @InjectView(R.id.level) TextView level;
    @InjectView(R.id.next_lvl) TextView nextLevel;
    @InjectView(R.id.gold) TextView gold;
    
    private int displayWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        AuthProviderFactory.onCreate(this);

        UserEventsProducer.produceGetUserStatsEvent(this);
        
        initUI();
    }

    private void initUI() {
        displayWidth = getResources().getDisplayMetrics().widthPixels;
        
        viewPager.setAdapter(new MainScreenFragmentsAdapter(getSupportFragmentManager()));
        viewPager.setPageTransformer(true, new ZoomOutPageTransformer());
    }

    @Subscribe
    public void onUserStatsObtained(GetUserStatsEvent event) {
        updateGold(event.getStats().getGold());
        updateLevels(event.getStats().getLvl());
        updateExp(event.getStats().getExp(), event.getStats().getMinLvlExp(), event.getStats().getMaxLvlExp());
        updateHP(event.getStats().getHp());
    }
    
    private void updateGold(int goldValue) {
        gold.setText(String.valueOf(goldValue));
    }
    
    private void updateLevels(int lvl) {
        level.setText(String.valueOf(lvl));
        nextLevel.setText(String.valueOf(lvl + 1));
    }
    
    private void updateHP(int hp) {
        hpValue.setText(hp + " / 100");
        hpBarMask.getLayoutParams().width = getViewWidth(displayWidth, hp, 0, 100);
    }
    
    private void updateExp(int exp, int minLvlExp, int maxLvlExp) {
        expValue.setText(exp + " / " + maxLvlExp);
        expBarMask.getLayoutParams().width = getViewWidth(getExpBarWidth(), exp, minLvlExp, maxLvlExp);
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
