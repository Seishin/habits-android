package co.naughtyspirit.habits.bus.events.habits;

import co.naughtyspirit.habits.bus.events.BaseFailureEvent;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/28/15.
 * *
 * * NaughtySpirit 2015
 */
public class HabitsFailureEvent extends BaseFailureEvent {
    public HabitsFailureEvent(String message) {
        this.message = message;
    }
}
