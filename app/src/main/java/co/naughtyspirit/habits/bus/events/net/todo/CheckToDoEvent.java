package co.naughtyspirit.habits.bus.events.net.todo;

import co.naughtyspirit.habits.net.models.todo.ToDo;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class CheckToDoEvent {
    private ToDo todo;

    public CheckToDoEvent(ToDo todo) {
        this.todo = todo;
    }

    public ToDo getToDo() {
        return todo;
    }
}
