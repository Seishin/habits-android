package co.naughtyspirit.habits.net.models.habit;

import java.util.ArrayList;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class HabitsList {
    private ArrayList<Habit> habits = new ArrayList<>();

    public ArrayList<Habit> getHabits() {
        return habits;
    }

    public void setHabits(ArrayList<Habit> habits) {
        this.habits = habits;
    }

    @Override
    public String toString() {
        return "HabitsList{" +
                "habits=" + habits +
                '}';
    }
}
