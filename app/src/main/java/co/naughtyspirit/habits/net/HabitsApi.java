package co.naughtyspirit.habits.net;

import co.naughtyspirit.habits.net.models.User;
import co.naughtyspirit.habits.net.models.UserStats;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/22/15.
 * 
 * NaughtySpirit 2015
 */
public interface HabitsApi {
    
    @POST("/users")
    void create(@Body User body, Callback<User> callback);
    
    @POST("/users/login")
    void login(@Body User body, Callback<User> callback);
    
    @GET("/users")
    void getUserByToken(@Header("Authorization") String token, Callback<User> callback);
    
    @GET("/users/{userId}")
    void getUserById(@Path("userId") String userId, Callback<User> callback);
    
    @GET("/users/stats")
    void getUserStats(@Header("Authorization") String token, Callback<UserStats> callback);
}
