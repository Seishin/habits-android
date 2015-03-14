package co.naughtyspirit.habits.bus.producers.net;

import com.squareup.otto.Produce;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.net.daily_task.CheckDailyTaskEvent;
import co.naughtyspirit.habits.bus.events.net.daily_task.CreateDailyTaskEvent;
import co.naughtyspirit.habits.bus.events.net.daily_task.DailyTasksFailureEvent;
import co.naughtyspirit.habits.bus.events.net.daily_task.DeleteDailyTaskEvent;
import co.naughtyspirit.habits.bus.events.net.daily_task.GetDailyTaskEvent;
import co.naughtyspirit.habits.bus.events.net.daily_task.GetDailyTasksEvent;
import co.naughtyspirit.habits.bus.events.net.daily_task.UncheckDailyTaskEvent;
import co.naughtyspirit.habits.bus.events.net.daily_task.UpdateDailyTaskEvent;
import co.naughtyspirit.habits.net.HabitsApiClient;
import co.naughtyspirit.habits.net.models.DeletedItem;
import co.naughtyspirit.habits.net.models.daily_task.DailyTask;
import co.naughtyspirit.habits.net.models.daily_task.DailyTasksList;
import co.naughtyspirit.habits.net.models.user.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class DailyTaskEventsProducer extends BaseNetEventProducer {
    
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

    @Produce
    public static void produceGetTaskEvent(User user, DailyTask task) {
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        
        HabitsApiClient.getClient().getDailyTask(user.getToken(), user.getId(), task.getId(), date, new Callback<DailyTask>() {
            @Override
            public void success(DailyTask task, Response response) {
                if (task != null) {
                    BusProvider.getInstance().post(new GetDailyTaskEvent(task));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new DailyTasksFailureEvent(getErrorMessage(error)));
            }
        });
    } 
    
    @Produce
    public static void produceGetTasksEvent(User user) {
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        HabitsApiClient.getClient().getAllDailyTasks(user.getToken(), user.getId(), date, new Callback<DailyTasksList>() {
            @Override
            public void success(DailyTasksList dailyTasksList, Response response) {
                if (dailyTasksList != null && dailyTasksList.getTasks().size() > 0) {
                    BusProvider.getInstance().post(new GetDailyTasksEvent(dailyTasksList));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new DailyTasksFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceCreateTaskEvent(User user, DailyTask task) {
        HabitsApiClient.getClient().createDailyTask(user.getToken(), user.getId(), task, new Callback<DailyTask>() {
            @Override
            public void success(DailyTask task, Response response) {
                if (task != null) {
                    BusProvider.getInstance().post(new CreateDailyTaskEvent(task));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new DailyTasksFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceCheckTaskEvent(User user, DailyTask task) {
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        
        HabitsApiClient.getClient().checkDailyTask(user.getToken(), user.getId(), date, task.getId(), new Callback<DailyTask>() {
            @Override
            public void success(DailyTask task, Response response) {
                if (task != null) {
                    BusProvider.getInstance().post(new CheckDailyTaskEvent(task));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new DailyTasksFailureEvent(getErrorMessage(error)));
            }
        });
    }

    @Produce
    public static void produceUnCheckTaskEvent(User user, DailyTask task) {
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());

        HabitsApiClient.getClient().uncheckDailyTask(user.getToken(), user.getId(), date, task.getId(), new Callback<DailyTask>() {
            @Override
            public void success(DailyTask task, Response response) {
                if (task != null) {
                    BusProvider.getInstance().post(new UncheckDailyTaskEvent(task));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new DailyTasksFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceUpdateTaskEvent(User user, DailyTask task) {
        String date = simpleDateFormat.format(Calendar.getInstance().getTime());
        
        HabitsApiClient.getClient().updateDailyTask(user.getToken(), user.getId(), date, task.getId(), new Callback<DailyTask>() {
            @Override
            public void success(DailyTask task, Response response) {
                if (task != null) {
                    BusProvider.getInstance().post(new UpdateDailyTaskEvent(task));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new DailyTasksFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceDeleteTaskEvent(User user, DailyTask task) {
        HabitsApiClient.getClient().deleteDailyTask(user.getToken(), user.getId(), task.getId(), new Callback<DeletedItem>() {
            @Override
            public void success(DeletedItem item, Response response) {
                if (item != null) {
                    BusProvider.getInstance().post(new DeleteDailyTaskEvent(item));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new DailyTasksFailureEvent(getErrorMessage(error)));
            }
        });
    }
}
