package co.naughtyspirit.habits.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.reward.BuyRewardEvent;
import co.naughtyspirit.habits.bus.events.reward.CreateRewardEvent;
import co.naughtyspirit.habits.bus.events.reward.DeleteRewardEvent;
import co.naughtyspirit.habits.bus.events.reward.GetRewardsEvent;
import co.naughtyspirit.habits.bus.events.reward.UpdateRewardEvent;
import co.naughtyspirit.habits.bus.producers.RewardEventsProducer;
import co.naughtyspirit.habits.bus.producers.UserEventsProducer;
import co.naughtyspirit.habits.net.models.DeletedItem;
import co.naughtyspirit.habits.net.models.reward.Reward;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/13/15.
 * *
 * * NaughtySpirit 2015
 */
public class RewardsListAdapter extends BaseAdapter {

    private static final String TAG = RewardsListAdapter.class.getName();

    private Context ctx;
    private ArrayList<Reward> rewards = new ArrayList<>();
    private LayoutInflater inflater;

    public RewardsListAdapter(Context ctx) {
        this.ctx = ctx;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        BusProvider.getInstance().register(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        final Reward reward = getItem(position);

        if (view == null) {
            view = inflater.inflate(R.layout.layout_cell_reward, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.rewardName.setText(reward.getText());
        viewHolder.rewardPrice.setText(reward.getGoldAsString());

        viewHolder.rewardPrice.setOnClickListener(null);
        viewHolder.rewardPrice.setOnClickListener(new CustomOnClickListener(reward));

        viewHolder.rewardDelete.setOnClickListener(null);
        viewHolder.rewardDelete.setOnClickListener(new CustomOnClickListener(reward));

        return view;
    }

    @Override
    public int getCount() {
        return rewards.size();
    }

    @Override
    public Reward getItem(int position) {
        return rewards.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Subscribe
    public void onRewardsObtainSuccess(GetRewardsEvent event) {
        addItems(event.getList().getRewards());
    }

    @Subscribe
    public void onCreateRewardSuccess(CreateRewardEvent event) {
        addItem(event.getReward());
    }

    @Subscribe
    public void onBuyRewardSuccess(BuyRewardEvent event) {
        updateItem(event.getReward());
        UserEventsProducer.produceGetUserStatsEvent(ctx);
    }

    @Subscribe
    public void onUpdateRewardSuccess(UpdateRewardEvent event) {
        updateItem(event.getReward());
    }

    @Subscribe
    public void onDeleteRewardSuccess(DeleteRewardEvent event) {
        deleteItem(event.getItem());
    }

    private synchronized void addItem(Reward reward) {
        rewards.add(0, reward);
        notifyDataSetChanged();
    }

    private void addItems(ArrayList<Reward> rewards) {
        this.rewards.addAll(rewards);
        notifyDataSetChanged();
    }

    private synchronized void updateItem(Reward reward) {
        for (Reward r : rewards) {
            if (r.getId().equals(reward.getId())) {
                r.setText(reward.getText());
                r.setGold(reward.getGold());
                break;
            }
        }
        notifyDataSetChanged();
    }

    private synchronized void deleteItem(DeletedItem item) {
        ArrayList<Reward> copyList = new ArrayList<>();
        copyList.addAll(rewards);

        for (Reward r : copyList) {
            if (r.getId().equals(item.getId())) {
                copyList.remove(r);
                break;
            }
        }

        if (copyList.size() != rewards.size()) {
            rewards.clear();
            rewards.addAll(copyList);
            notifyDataSetChanged();
        }
    }

    static class ViewHolder {
        @InjectView(R.id.tv_reward_name) TextView rewardName;
        @InjectView(R.id.btn_buy_reward) Button rewardPrice;
        @InjectView(R.id.btn_delete_reward) Button rewardDelete;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    private class CustomOnClickListener implements View.OnClickListener {

        private final Reward reward;

        public CustomOnClickListener(Reward reward) {
            this.reward = reward;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_buy_reward:
                    RewardEventsProducer.produceBuyRewardEvent(AuthProviderFactory.getProvider().getUser(), reward);
                    break;

                case R.id.btn_delete_reward:
                    RewardEventsProducer.produceDeleteRewardEvent(AuthProviderFactory.getProvider().getUser(), reward);
                    break;
            }
        }
    }
}
