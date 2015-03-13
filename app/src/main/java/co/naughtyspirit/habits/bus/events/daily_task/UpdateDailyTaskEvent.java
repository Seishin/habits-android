package co.naughtyspirit.habits.bus.events.daily_task;

import co.naughtyspirit.habits.net.models.daily_task.DailyTask;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class UpdateDailyTaskEvent {
    private DailyTask task;

    public UpdateDailyTaskEvent(DailyTask task) {
        this.task = task;
    }

    public DailyTask getTask() {
        return task;
    }
}
