package co.naughtyspirit.habits.bus.events.net.reward;

import co.naughtyspirit.habits.bus.events.BaseFailureEvent;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/13/15.
 * *
 * * NaughtySpirit 2015
 */
public class RewardsFailureEvent extends BaseFailureEvent {
    public RewardsFailureEvent(String message) {
        this.message = message;
    }
}
