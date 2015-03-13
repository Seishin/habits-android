package co.naughtyspirit.habits.bus.events.habit;

import co.naughtyspirit.habits.net.models.habit.Habit;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class IncrementHabitEvent {

    private final Habit habit;

    public IncrementHabitEvent(Habit habit) {
        this.habit = habit;
    }

    public Habit getHabit() {
        return habit;
    }
}
