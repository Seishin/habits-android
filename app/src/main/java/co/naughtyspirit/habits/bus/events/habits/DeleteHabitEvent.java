package co.naughtyspirit.habits.bus.events.habits;

import co.naughtyspirit.habits.net.models.DeletedItem;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/8/15.
 * *
 * * NaughtySpirit 2015
 */
public class DeleteHabitEvent {

    private DeletedItem item;

    public DeleteHabitEvent(DeletedItem item) {
        this.item = item;
    }

    public DeletedItem getItem() {
        return item;
    }
}
