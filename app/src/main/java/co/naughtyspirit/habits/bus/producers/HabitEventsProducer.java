package co.naughtyspirit.habits.bus.producers;

import android.util.Log;

import com.squareup.otto.Produce;

import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.CreateHabitEvent;
import co.naughtyspirit.habits.bus.events.GetHabitsEvent;
import co.naughtyspirit.habits.bus.events.HabitsFailureEvent;
import co.naughtyspirit.habits.bus.events.IncrementHabitEvent;
import co.naughtyspirit.habits.net.HabitsApiClient;
import co.naughtyspirit.habits.net.models.Habit;
import co.naughtyspirit.habits.net.models.HabitsList;
import co.naughtyspirit.habits.net.models.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class HabitEventsProducer extends BaseEventProducer {
    
    @Produce
    public static void produceGetHabitsEvent(User user) {
        HabitsApiClient.getClient().getHabits(user.getToken(), user.getId(), new Callback<HabitsList>() {
            @Override
            public void success(HabitsList habitsList, Response response) {
                if (habitsList != null && habitsList.getHabits().size() > 0) {
                    BusProvider.getInstance().post(new GetHabitsEvent(habitsList));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new HabitsFailureEvent(getErrorMessage(error)));
            }
        });
    }

    @Produce
    public static void produceCreateHabitEvent(User user, Habit habit) {
        HabitsApiClient.getClient().createHabit(user.getToken(), user.getId(), habit, new Callback<Habit>() {
            @Override
            public void success(Habit habit, Response response) {
                if (habit != null) {
                    Log.e("tag", habit.toString());
                    BusProvider.getInstance().post(new CreateHabitEvent(habit));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new HabitsFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceIncrementHabitEvent(User user, Habit habit) {
        HabitsApiClient.getClient().incrementHabit(user.getToken(), user.getId(), habit.getId(), new Callback<Response>() {
            @Override
            public void success(Response response, Response response2) {
                if (response.getStatus() == 200) {
                    BusProvider.getInstance().post(new IncrementHabitEvent());
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new HabitsFailureEvent(getErrorMessage(error)));
            }
        });
    }
}
