package co.naughtyspirit.habits.bus.events.net.todo;

import co.naughtyspirit.habits.bus.events.BaseFailureEvent;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class ToDoFailureEvent extends BaseFailureEvent {
    
    public ToDoFailureEvent(String message) {
        this.message = message;
    }
}
