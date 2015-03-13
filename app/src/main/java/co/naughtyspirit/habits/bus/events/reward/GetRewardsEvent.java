package co.naughtyspirit.habits.bus.events.reward;

import co.naughtyspirit.habits.net.models.reward.RewardsList;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/13/15.
 * *
 * * NaughtySpirit 2015
 */
public class GetRewardsEvent {
    private RewardsList list;

    public GetRewardsEvent(RewardsList list) {
        this.list = list;
    }

    public RewardsList getList() {
        return list;
    }
}
