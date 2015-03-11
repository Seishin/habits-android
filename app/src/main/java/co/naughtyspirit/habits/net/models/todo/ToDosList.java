package co.naughtyspirit.habits.net.models.todo;

import java.util.ArrayList;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class ToDosList {
    private ArrayList<ToDo> todos = new ArrayList<>();

    public ArrayList<ToDo> getTodos() {
        return todos;
    }

    public void setTodos(ArrayList<ToDo> todos) {
        this.todos = todos;
    }

    @Override
    public String toString() {
        return "DailyTasksList{" +
                "todos=" + todos +
                '}';
    }
}
