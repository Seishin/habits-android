package co.naughtyspirit.habits.bus.producers;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Produce;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.AuthFailureEvent;
import co.naughtyspirit.habits.bus.events.CreateUserEvent;
import co.naughtyspirit.habits.bus.events.GetUserStatsEvent;
import co.naughtyspirit.habits.bus.events.LoginUserEvent;
import co.naughtyspirit.habits.net.HabitsApiClient;
import co.naughtyspirit.habits.net.models.User;
import co.naughtyspirit.habits.net.models.UserStats;
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

    private static final String TAG = UserEventsProducer.class.getName();

    @Produce
    public static void produceUserCreatedEvent(User user) {
        HabitsApiClient.getClient().create(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                if (response.getStatus() == 201 && user != null) {
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
    public static void produceUserLoginEvent(User user) {
        HabitsApiClient.getClient().login(user, new Callback<User>() {
            @Override
            public void success(User user, Response response) {
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
        HabitsApiClient.getClient().getUserStats(AuthProviderFactory.getProvider(ctx).getAuthToken(), new Callback<UserStats>() {
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
    
    private static String getErrorMessage(RetrofitError error) {
        String message = null;
        
        try {
            BufferedReader streamReader = new BufferedReader(new InputStreamReader(
                    error.getResponse().getBody().in(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);
            JSONObject object = new JSONObject(responseStrBuilder.toString());
            message =  object.getString("message");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        } 
            
        return message;
    }
}
