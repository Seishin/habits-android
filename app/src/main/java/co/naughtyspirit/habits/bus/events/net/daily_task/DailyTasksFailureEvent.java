package co.naughtyspirit.habits.bus.events.net.daily_task;

import co.naughtyspirit.habits.bus.events.BaseFailureEvent;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class DailyTasksFailureEvent extends BaseFailureEvent {
    
    public DailyTasksFailureEvent(String message) {
        this.message = message;
    }
}
