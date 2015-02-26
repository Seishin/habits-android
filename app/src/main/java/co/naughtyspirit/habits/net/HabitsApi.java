package co.naughtyspirit.habits.net;

import co.naughtyspirit.habits.net.models.User;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/22/15.
 * <p/>
 * NaughtySpirit 2015
 */
public interface HabitsApi {
    
    @POST("/users/create")
    void create(@Body User body, Callback<User> callback);
}
