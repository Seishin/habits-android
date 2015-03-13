package co.naughtyspirit.habits.bus.events.daily_task;

import co.naughtyspirit.habits.net.models.daily_task.DailyTasksList;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class GetDailyTasksEvent {

    private DailyTasksList list;

    public GetDailyTasksEvent(DailyTasksList list) {
        this.list = list;
    }

    public DailyTasksList getList() {
        return list;
    }
}