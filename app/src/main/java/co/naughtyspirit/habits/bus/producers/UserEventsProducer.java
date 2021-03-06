package co.naughtyspirit.habits.bus.producers;

import android.content.Context;

import com.squareup.otto.Produce;

import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.users.AuthFailureEvent;
import co.naughtyspirit.habits.bus.events.users.CreateUserEvent;
import co.naughtyspirit.habits.bus.events.users.GetUserStatsEvent;
import co.naughtyspirit.habits.bus.events.users.LoginUserEvent;
import co.naughtyspirit.habits.net.HabitsApiClient;
import co.naughtyspirit.habits.net.models.user.User;
import co.naughtyspirit.habits.net.models.user.UserStats;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/22/15.
 * *
 * * NaughtySpirit 2015
 */
public class UserEventsProducer extends BaseEventProducer {

    private static final String TAG = UserEventsProducer.class.getName();

    @Produce
    public static void produceUserCreatedEvent(final Context ctx, final String provider, User user) {
        HabitsApiClient.getClient().create(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                if (response.getStatus() == 201 && user != null) {
                    AuthProviderFactory.setAuthProvider(provider);
                    BusProvider.getInstance().post(new CreateUserEvent(user));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new AuthFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceUserLoginEvent(final Context ctx, final String provider, User user) {
        HabitsApiClient.getClient().login(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                AuthProviderFactory.setAuthProvider(provider);
                BusProvider.getInstance().post(new LoginUserEvent(user));
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new AuthFailureEvent(getErrorMessage(error)));
            }
        });
    }
    
    @Produce
    public static void produceGetUserStatsEvent(Context ctx) {
        HabitsApiClient.getClient().getUserStats(AuthProviderFactory.getProvider().getUser().getToken(),
                AuthProviderFactory.getProvider().getUser().getId(), new Callback<UserStats>() {
            @Override
            public void success(UserStats stats, Response response) {  
                BusProvider.getInstance().post(new GetUserStatsEvent(stats));
            }

            @Override
            public void failure(RetrofitError error) {
                BusProvider.getInstance().post(new AuthFailureEvent(getErrorMessage(error)));
            }
        });
    }
}
