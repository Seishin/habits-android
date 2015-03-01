package co.naughtyspirit.habits.bus.events;

import co.naughtyspirit.habits.net.models.HabitsList;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class GetHabitsEvent {

    private final HabitsList list;

    public GetHabitsEvent(HabitsList list) {
        this.list = list;
    }

    public HabitsList getList() {
        return list;
    }
}
