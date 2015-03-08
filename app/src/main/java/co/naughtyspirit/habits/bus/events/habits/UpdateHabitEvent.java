package co.naughtyspirit.habits.bus.events.habits;

import co.naughtyspirit.habits.net.models.habit.Habit;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/8/15.
 * *
 * * NaughtySpirit 2015
 */
public class UpdateHabitEvent {
    private Habit habit;

    public UpdateHabitEvent(Habit habit) {
        this.habit = habit;
    }

    public Habit getHabit() {
        return habit;
    }
}
