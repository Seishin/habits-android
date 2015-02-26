package co.naughtyspirit.habits.bus.producers;

import com.squareup.otto.Produce;

import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.CreateUserEvent;
import co.naughtyspirit.habits.bus.events.LoginUserEvent;
import co.naughtyspirit.habits.net.HabitsApi;
import co.naughtyspirit.habits.net.HabitsApiClient;
import co.naughtyspirit.habits.net.models.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/22/15.
 * *
 * * NaughtySpirit 2015
 */
public class UserEventsProducer {
    
    @Produce
    public static void produceUserCreatedEvent(User user) {
        HabitsApiClient.getClient().create(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                BusProvider.getInstance().post(new CreateUserEvent(user));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });
    }
    
    @Produce
    public static void produceUserLoginEvent(User user) {
        HabitsApiClient.getClient().login(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                if (user != null) {
                    BusProvider.getInstance().post(new LoginUserEvent(user));
                }
            }

            @Override
            public void failure(RetrofitError error) {
            }
        });
    }
}
