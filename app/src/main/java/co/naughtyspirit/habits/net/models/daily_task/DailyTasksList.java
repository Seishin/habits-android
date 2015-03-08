package co.naughtyspirit.habits.net.models.daily_task;

import java.util.ArrayList;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class DailyTasksList {
    private ArrayList<DailyTask> tasks = new ArrayList<>();

    public ArrayList<DailyTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<DailyTask> tasks) {
        this.tasks = tasks;
    }

    @Override
    public String toString() {
        return "DailyTasksList{" +
                "tasks=" + tasks +
                '}';
    }
}
