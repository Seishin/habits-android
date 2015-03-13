package co.naughtyspirit.habits.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.reward.RewardsFailureEvent;
import co.naughtyspirit.habits.bus.producers.RewardEventsProducer;
import co.naughtyspirit.habits.net.models.reward.Reward;
import co.naughtyspirit.habits.net.models.user.User;
import co.naughtyspirit.habits.ui.adapters.RewardsListAdapter;
import co.naughtyspirit.habits.utils.WindowUtils;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class RewardsFragment extends Fragment {

    private static final String TAG = RewardsFragment.class.getName();
    
    private Activity activity;
    
    @InjectView(R.id.lv_rewards) ListView rewardsList;
    @InjectView(R.id.et_reward_text) EditText rewardText;
    @InjectView(R.id.et_reward_price) EditText rewardPrice;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rewards, container, false);
        ButterKnife.inject(this, view);

        RewardEventsProducer.produceGetRewardsEvent(AuthProviderFactory.getProvider().getUser());

        initUI();

        return view;
    }

    private void initUI() {
        rewardsList.setAdapter(new RewardsListAdapter(activity));
    }

    @OnClick(R.id.btn_submit)
    public void submitTask() {
        User user = AuthProviderFactory.getProvider().getUser();

        if (TextUtils.isEmpty(rewardText.getText())
                && TextUtils.isEmpty(rewardPrice.getText())) {
            rewardText.setError("Couldn't be empty");
            return;
        }

        RewardEventsProducer.produceCreateRewardEvent(user, new Reward(rewardText.getText().toString(),
                    Integer.valueOf(rewardPrice.getText().toString())));
        rewardText.getText().clear();
        rewardPrice.getText().clear();

        WindowUtils.hideSoftKeyboard(activity, rewardPrice);
    }

    @Subscribe
    public void onRewardObtainFailure(RewardsFailureEvent event) {
        Log.e(TAG, event.getMessage());
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        this.activity = activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}
