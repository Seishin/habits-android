package co.naughtyspirit.habits.bus.events.net.reward;

import co.naughtyspirit.habits.net.models.reward.Reward;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/13/15.
 * *
 * * NaughtySpirit 2015
 */
public class BuyRewardEvent {
    private Reward reward;

    public BuyRewardEvent(Reward reward) {
        this.reward = reward;
    }

    public Reward getReward() {
        return reward;
    }
}
