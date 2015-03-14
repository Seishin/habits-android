package co.naughtyspirit.habits.bus.producers.net;

import com.squareup.otto.Produce;

import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.net.reward.BuyRewardEvent;
import co.naughtyspirit.habits.bus.events.net.reward.CreateRewardEvent;
import co.naughtyspirit.habits.bus.events.net.reward.DeleteRewardEvent;
import co.naughtyspirit.habits.bus.events.net.reward.GetRewardEvent;
import co.naughtyspirit.habits.bus.events.net.reward.GetRewardsEvent;
import co.naughtyspirit.habits.bus.events.net.reward.RewardsFailureEvent;
import co.naughtyspirit.habits.bus.events.net.reward.UpdateRewardEvent;
import co.naughtyspirit.habits.net.HabitsApiClient;
import co.naughtyspirit.habits.net.models.DeletedItem;
import co.naughtyspirit.habits.net.models.reward.Reward;
import co.naughtyspirit.habits.net.models.reward.RewardsList;
import co.naughtyspirit.habits.net.models.user.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/13/15.
 * *
 * * NaughtySpirit 2015
 */
public class RewardEventsProducer extends BaseNetEventProducer {

    @Produce
    public static void produceGetRewardEvent(User user, Reward reward) {
        HabitsApiClient.getClient().getReward(user.getToken(), reward.getId(), user.getId(), new Callback<Reward>() {
            @Override
            public void success(Reward reward, Response response) {
                if (reward != null) {
                    BusProvider.getInstance().post(new GetRewardEvent(reward));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new RewardsFailureEvent(getErrorMessage(error)));
            }
        });
    }

    @Produce
    public static void produceGetRewardsEvent(User user) {
        HabitsApiClient.getClient().getRewards(user.getToken(), user.getId(), new Callback<RewardsList>() {
            @Override
            public void success(RewardsList rewardsList, Response response) {
                if (rewardsList != null && rewardsList.getRewards().size() >= 0) {
                    BusProvider.getInstance().post(new GetRewardsEvent(rewardsList));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new RewardsFailureEvent(getErrorMessage(error)));
            }
        });
    }

    @Produce
    public static void produceCreateRewardEvent(User user, Reward reward) {
        HabitsApiClient.getClient().createReward(user.getToken(), user.getId(), reward, new Callback<Reward>() {
            @Override
            public void success(Reward reward, Response response) {
                if (reward != null) {
                    BusProvider.getInstance().post(new CreateRewardEvent(reward));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new RewardsFailureEvent(getErrorMessage(error)));
            }
        });
    }

    @Produce
    public static void produceBuyRewardEvent(User user, Reward reward) {
        HabitsApiClient.getClient().buyReward(user.getToken(), user.getId(), reward.getId(), new Callback<Reward>() {
            @Override
            public void success(Reward reward, Response response) {
                if (reward != null) {
                    BusProvider.getInstance().post(new BuyRewardEvent(reward));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new RewardsFailureEvent(getErrorMessage(error)));
            }
        });
    }

    @Produce
    public static void produceUpdateRewardEvent(User user, Reward reward) {
        HabitsApiClient.getClient().updateReward(user.getToken(), user.getId(), reward.getId(), new Callback<Reward>() {
            @Override
            public void success(Reward reward, Response response) {
                if (reward != null) {
                    BusProvider.getInstance().post(new UpdateRewardEvent(reward));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new RewardsFailureEvent(getErrorMessage(error)));
            }
        });
    }

    @Produce
    public static void produceDeleteRewardEvent(User user, Reward reward) {
        HabitsApiClient.getClient().deleteReward(user.getToken(), user.getId(), reward.getId(), new Callback<DeletedItem>() {
            @Override
            public void success(DeletedItem item, Response response) {
                if (item != null) {
                    BusProvider.getInstance().post(new DeleteRewardEvent(item));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new RewardsFailureEvent(getErrorMessage(error)));
            }
        });
    }
}
