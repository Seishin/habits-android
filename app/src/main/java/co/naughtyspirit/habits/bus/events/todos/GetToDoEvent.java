package co.naughtyspirit.habits.bus.events.todos;

import co.naughtyspirit.habits.net.models.todo.ToDo;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class GetToDoEvent {
    private ToDo todo;

    public GetToDoEvent(ToDo todo) {
        this.todo = todo;
    }

    public ToDo getToDo() {
        return todo;
    }
}
