package co.naughtyspirit.habits.bus.producers;

import com.squareup.otto.Produce;

import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.todos.CheckToDoEvent;
import co.naughtyspirit.habits.bus.events.todos.CreateToDoEvent;
import co.naughtyspirit.habits.bus.events.todos.DeleteToDoEvent;
import co.naughtyspirit.habits.bus.events.todos.GetToDoEvent;
import co.naughtyspirit.habits.bus.events.todos.GetToDosEvent;
import co.naughtyspirit.habits.bus.events.todos.ToDoFailureEvent;
import co.naughtyspirit.habits.bus.events.todos.UncheckToDoEvent;
import co.naughtyspirit.habits.bus.events.todos.UpdateToDoEvent;
import co.naughtyspirit.habits.net.HabitsApiClient;
import co.naughtyspirit.habits.net.models.DeletedItem;
import co.naughtyspirit.habits.net.models.todo.ToDo;
import co.naughtyspirit.habits.net.models.todo.ToDosList;
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
public class ToDoEventsProducer extends BaseEventProducer {
    
    @Produce
    public static void produceGetToDoEvent(User user, ToDo todo) {
        HabitsApiClient.getClient().getToDo(user.getToken(), user.getId(), todo.getId(), new Callback<ToDo>() {
            @Override
            public void success(ToDo todo, Response response) {
                if (todo != null) {
                    BusProvider.getInstance().post(new GetToDoEvent(todo));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new ToDoFailureEvent(getErrorMessage(error)));
            }
        });
    } 
    
    @Produce
    public static void produceGetToDosEvent(User user) {
        HabitsApiClient.getClient().getAllToDos(user.getToken(), user.getId(), new Callback<ToDosList>() {
            @Override
            public void success(ToDosList toDosList, Response response) {
                if (toDosList != null && toDosList.getTodos().size() > 0) {
                    BusProvider.getInstance().post(new GetToDosEvent(toDosList));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new ToDoFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceCreateToDoEvent(User user, ToDo todo) {
        HabitsApiClient.getClient().createToDo(user.getToken(), user.getId(), todo, new Callback<ToDo>() {
            @Override
            public void success(ToDo todo, Response response) {
                if (todo != null) {
                    BusProvider.getInstance().post(new CreateToDoEvent(todo));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new ToDoFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceCheckToDoEvent(User user, ToDo todo) {
        HabitsApiClient.getClient().checkToDo(user.getToken(), user.getId(), todo.getId(), new Callback<ToDo>() {
            @Override
            public void success(ToDo todo, Response response) {
                if (todo != null) {
                    BusProvider.getInstance().post(new CheckToDoEvent(todo));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new ToDoFailureEvent(getErrorMessage(error)));
            }
        });
    }

    @Produce
    public static void produceUnCheckToDoEvent(User user, ToDo todo) {
        HabitsApiClient.getClient().uncheckToDo(user.getToken(), user.getId(), todo.getId(), new Callback<ToDo>() {
            @Override
            public void success(ToDo todo, Response response) {
                if (todo != null) {
                    BusProvider.getInstance().post(new UncheckToDoEvent(todo));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new ToDoFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceUpdateToDoEvent(User user, ToDo todo) {
        HabitsApiClient.getClient().updateToDo(user.getToken(), user.getId(), todo.getId(), new Callback<ToDo>() {
            @Override
            public void success(ToDo todo, Response response) {
                if (todo != null) {
                    BusProvider.getInstance().post(new UpdateToDoEvent(todo));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new ToDoFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceDeleteToDoEvent(User user, ToDo todo) {
        HabitsApiClient.getClient().deleteToDo(user.getToken(), user.getId(), todo.getId(), new Callback<DeletedItem>() {
            @Override
            public void success(DeletedItem item, Response response) {
                if (item != null) {
                    BusProvider.getInstance().post(new DeleteToDoEvent(item));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new ToDoFailureEvent(getErrorMessage(error)));
            }
        });
    }
}
