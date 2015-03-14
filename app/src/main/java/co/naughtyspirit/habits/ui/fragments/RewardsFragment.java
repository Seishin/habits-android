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
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.net.reward.RewardsFailureEvent;
import co.naughtyspirit.habits.bus.producers.net.RewardEventsProducer;
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
public class RewardsFragment extends BaseFragment {

    private static final String TAG = RewardsFragment.class.getName();
    
    private Activity activity;

    @InjectView(R.id.header) TextView header;
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

        RewardEventsProducer.produceGetRewardsEvent(AuthProviderFactory.getProvider(activity).getUser());

        initUI();

        return view;
    }

    private void initUI() {
        header.setTypeface(getHelvetica());
        rewardText.setTypeface(getHelveticaLight());
        rewardPrice.setTypeface(getHelveticaLight());
        rewardsList.setAdapter(new RewardsListAdapter(activity));
    }

    @OnClick(R.id.btn_submit)
    public void submitTask() {
        User user = AuthProviderFactory.getProvider(activity).getUser();

        if (TextUtils.isEmpty(rewardText.getText())) {
            Toast.makeText(activity, "Reward text couldn't be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(rewardPrice.getText())) {
            Toast.makeText(activity, "Reward price couldn't be empty!", Toast.LENGTH_SHORT).show();
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
        Toast.makeText(activity, event.getMessage(), Toast.LENGTH_LONG).show();
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
