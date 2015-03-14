package co.naughtyspirit.habits.bus.producers.net;

import com.squareup.otto.Produce;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.net.habit.CreateHabitEvent;
import co.naughtyspirit.habits.bus.events.net.habit.DeleteHabitEvent;
import co.naughtyspirit.habits.bus.events.net.habit.GetHabitEvent;
import co.naughtyspirit.habits.bus.events.net.habit.GetHabitsEvent;
import co.naughtyspirit.habits.bus.events.net.habit.HabitsFailureEvent;
import co.naughtyspirit.habits.bus.events.net.habit.IncrementHabitEvent;
import co.naughtyspirit.habits.bus.events.net.habit.UpdateHabitEvent;
import co.naughtyspirit.habits.net.HabitsApiClient;
import co.naughtyspirit.habits.net.models.DeletedItem;
import co.naughtyspirit.habits.net.models.habit.Habit;
import co.naughtyspirit.habits.net.models.habit.HabitsList;
import co.naughtyspirit.habits.net.models.user.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class HabitEventsProducer extends BaseNetEventProducer {
    
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Produce
    public static void produceGetHabitEvent(User user, Habit habit) {
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        HabitsApiClient.getClient().getHabit(user.getToken(), habit.getId(), user.getId(), date, new Callback<Habit>() {
            @Override
            public void success(Habit habit, Response response) {
                if (habit != null) {
                    BusProvider.getInstance().post(new GetHabitEvent(habit));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new HabitsFailureEvent(getErrorMessage(error)));
            }
        });
    }

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

    @Produce
    public static void produceUpdateHabitEvent(User user, Habit habit) {
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        HabitsApiClient.getClient().updateHabit(user.getToken(), user.getId(), date, habit.getId(), habit, new Callback<Habit>() {
            @Override
            public void success(Habit habit, Response response) {
                if (habit != null) {
                    BusProvider.getInstance().post(new UpdateHabitEvent(habit));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new HabitsFailureEvent(getErrorMessage(error)));
            }
        });
    }

    @Produce
    public static void produceDeleteHabitEvent(User user, Habit habit) {
        HabitsApiClient.getClient().deleteHabit(user.getToken(), user.getId(), habit.getId(), new Callback<DeletedItem>() {
            @Override
            public void success(DeletedItem item, Response response) {
                if (item != null) {
                    BusProvider.getInstance().post(new DeleteHabitEvent(item));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new HabitsFailureEvent(getErrorMessage(error)));
            }
        });
    }
}
