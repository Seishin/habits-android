package co.naughtyspirit.habits.bus.events.daily_tasks;

import co.naughtyspirit.habits.net.models.daily_task.DailyTask;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class CheckDailyTaskEvent {
    private DailyTask task;

    public CheckDailyTaskEvent(DailyTask task) {
        this.task = task;
    }

    public DailyTask getTask() {
        return task;
    }
}
