package co.naughtyspirit.habits.bus.events.reward;

import co.naughtyspirit.habits.net.models.DeletedItem;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/13/15.
 * *
 * * NaughtySpirit 2015
 */
public class DeleteRewardEvent {
    private DeletedItem item;

    public DeleteRewardEvent(DeletedItem item) {
        this.item = item;
    }

    public DeletedItem getItem() {
        return item;
    }
}
