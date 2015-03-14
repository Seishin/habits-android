package co.naughtyspirit.habits.bus.events.net.habit;

import co.naughtyspirit.habits.net.models.habit.Habit;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class CreateHabitEvent {

    private final Habit habit;

    public CreateHabitEvent(Habit habit) {
        this.habit = habit;
    }

    public Habit getHabit() {
        return habit;
    }
}
