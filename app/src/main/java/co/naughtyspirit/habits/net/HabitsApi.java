package co.naughtyspirit.habits.net;

import co.naughtyspirit.habits.net.models.Habit;
import co.naughtyspirit.habits.net.models.HabitsList;
import co.naughtyspirit.habits.net.models.User;
import co.naughtyspirit.habits.net.models.UserStats;
import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

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
    
    @GET("/stats/{userId}")
    void getUserStats(@Header("Authorization") String token, @Path("userId") String userId, Callback<UserStats> callback);
    
    @GET("/habits/all/")
    void getHabits(@Header("Authorization") String token, @Query("userId") String userId, Callback<HabitsList> cb);
    
    @POST("/habits/")
    void createHabit(@Header("Authorization") String token, @Query("userId") String userId, @Body Habit habit, Callback<Habit> cb);
    
    @POST("/habits/increment/{habitId}/")
    void incrementHabit(@Header("Authorization") String token, @Query("userId") String userId, @Path("habitId") String habitId, Callback<Habit> cb);
}
