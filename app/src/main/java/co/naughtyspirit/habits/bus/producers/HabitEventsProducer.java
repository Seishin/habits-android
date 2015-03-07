package co.naughtyspirit.habits.bus.producers;

import android.util.Log;

import com.squareup.otto.Produce;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.habits.CreateHabitEvent;
import co.naughtyspirit.habits.bus.events.habits.GetHabitsEvent;
import co.naughtyspirit.habits.bus.events.habits.HabitsFailureEvent;
import co.naughtyspirit.habits.bus.events.habits.IncrementHabitEvent;
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
    
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    
    @Produce
    public static void produceGetHabitsEvent(User user) {
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        
        HabitsApiClient.getClient().getHabits(user.getToken(), user.getId(), date, new Callback<HabitsList>() {
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
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        
        HabitsApiClient.getClient().incrementHabit(user.getToken(), user.getId(), date, habit.getId(), new Callback<Habit>() {
            @Override
            public void success(Habit habit, Response response) {
                if (response.getStatus() == 200) {
                    BusProvider.getInstance().post(new IncrementHabitEvent(habit));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new HabitsFailureEvent(getErrorMessage(error)));
            }
        });
    }
}
