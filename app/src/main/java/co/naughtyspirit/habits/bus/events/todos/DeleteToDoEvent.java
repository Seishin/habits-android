package co.naughtyspirit.habits.bus.events.todos;

import co.naughtyspirit.habits.net.models.DeletedItem;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class DeleteToDoEvent {
    private DeletedItem item;

    public DeleteToDoEvent(DeletedItem item) {
        this.item = item;
    }

    public DeletedItem getItem() {
        return item;
    }
}
