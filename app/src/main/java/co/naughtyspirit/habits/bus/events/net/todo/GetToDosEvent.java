package co.naughtyspirit.habits.bus.events.net.todo;

import co.naughtyspirit.habits.net.models.todo.ToDosList;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class GetToDosEvent {

    private ToDosList list;

    public GetToDosEvent(ToDosList list) {
        this.list = list;
    }

    public ToDosList getList() {
        return list;
    }
}