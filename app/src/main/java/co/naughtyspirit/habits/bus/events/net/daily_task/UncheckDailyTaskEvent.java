package co.naughtyspirit.habits.bus.events.net.daily_task;

import co.naughtyspirit.habits.net.models.daily_task.DailyTask;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class UncheckDailyTaskEvent {
    private DailyTask task;

    public UncheckDailyTaskEvent(DailyTask task) {
        this.task = task;
    }

    public DailyTask getTask() {
        return task;
    }
}
