package co.naughtyspirit.habits.bus.events.habits;

import co.naughtyspirit.habits.net.models.habit.Habit;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/8/15.
 * *
 * * NaughtySpirit 2015
 */
public class GetHabitEvent {
    private Habit habit;

    public GetHabitEvent(Habit habit) {
        this.habit = habit;
    }

    public Habit getHabit() {
        return habit;
    }
}
