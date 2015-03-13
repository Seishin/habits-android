package co.naughtyspirit.habits.bus.events.daily_task;

import co.naughtyspirit.habits.net.models.DeletedItem;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class DeleteDailyTaskEvent {
    private DeletedItem item;

    public DeleteDailyTaskEvent(DeletedItem item) {
        this.item = item;
    }

    public DeletedItem getItem() {
        return item;
    }
}
