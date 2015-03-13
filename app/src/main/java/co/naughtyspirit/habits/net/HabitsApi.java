package co.naughtyspirit.habits.net;

import co.naughtyspirit.habits.net.models.DeletedItem;
import co.naughtyspirit.habits.net.models.daily_task.DailyTask;
import co.naughtyspirit.habits.net.models.daily_task.DailyTasksList;
import co.naughtyspirit.habits.net.models.habit.Habit;
import co.naughtyspirit.habits.net.models.habit.HabitsList;
import co.naughtyspirit.habits.net.models.reward.Reward;
import co.naughtyspirit.habits.net.models.reward.RewardsList;
import co.naughtyspirit.habits.net.models.todo.ToDo;
import co.naughtyspirit.habits.net.models.todo.ToDosList;
import co.naughtyspirit.habits.net.models.user.User;
import co.naughtyspirit.habits.net.models.user.UserStats;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/22/15.
 * 
 * NaughtySpirit 2015
 */
public interface HabitsApi {

    /**
     *  User APIs
     */

    @POST("/users")
    void create(@Body User body, Callback<User> callback);
    
    @POST("/users/login")
    void login(@Body User body, Callback<User> callback);
    
    @GET("/users")
    void getUserByToken(@Header("Authorization") String token, Callback<User> callback);
    
    @GET("/users/{userId}")
    void getUserById(@Path("userId") String userId, Callback<User> callback);

    /**
     *  Stats APIs
     */

    @GET("/stats/{userId}")
    void getUserStats(@Header("Authorization") String token, @Path("userId") String userId, Callback<UserStats> callback);

    /**
     *  Habit APIs
     */

    @GET("/habits/{habitId}/")
    void getHabit(@Header("Authorization") String token, @Path("habitId") String taskId, @Query("userId") String userId, @Query("date") String date, Callback<Habit> cb);

    @GET("/habits/all/")
    void getHabits(@Header("Authorization") String token, @Query("userId") String userId, @Query("date") String date, Callback<HabitsList> cb);
    
    @POST("/habits/")
    void createHabit(@Header("Authorization") String token, @Query("userId") String userId, @Body Habit habit, Callback<Habit> cb);
    
    @POST("/habits/increment/{habitId}/")
    void incrementHabit(@Header("Authorization") String token, @Query("userId") String userId, @Query("date") String date, @Path("habitId") String habitId, Callback<Habit> cb);

    @PUT("/habits/{habitId}/")
    void updateHabit(@Header("Authorization") String token, @Query("userId") String userId, @Query("date") String date, @Path("habitId") String habitId, @Body Habit habit, Callback<Habit> cb);

    @DELETE("/habits/{habitId}/")
    void deleteHabit(@Header("Authorization") String token, @Query("userId") String userId, @Path("habitId") String taskId, Callback<DeletedItem> cb);

    /**
     *  Daily Task APIs
     */
    @GET("/daily-tasks/{taskId}/")
    void getDailyTask(@Header("Authorization") String token, @Path("taskId") String taskId, @Query("userId") String userId, @Query("date") String date, Callback<DailyTask> cb);

    @GET("/daily-tasks/all/")
    void getAllDailyTasks(@Header("Authorization") String token, @Query("userId") String userId, @Query("date") String date, Callback<DailyTasksList> cb);

    @POST("/daily-tasks/")
    void createDailyTask(@Header("Authorization") String token, @Query("userId") String userId, @Body DailyTask task, Callback<DailyTask> cb);

    @POST("/daily-tasks/{taskId}/check/")
    void checkDailyTask(@Header("Authorization") String token, @Query("userId") String userId, @Query("date") String date, @Path("taskId") String taskId, Callback<DailyTask> cb);

    @POST("/daily-tasks/{taskId}/uncheck/")
    void uncheckDailyTask(@Header("Authorization") String token, @Query("userId") String userId, @Query("date") String date, @Path("taskId") String taskId, Callback<DailyTask> cb);

    @PUT("/daily-tasks/{taskId}/")
    void updateDailyTask(@Header("Authorization") String token, @Query("userId") String userId, @Query("date") String date, @Path("taskId") String taskId, Callback<DailyTask> cb);
    
    @DELETE("/daily-tasks/{taskId}/")
    void deleteDailyTask(@Header("Authorization") String token, @Query("userId") String userId, @Path("taskId") String taskId, Callback<DeletedItem> cb);

    /**
     *  To Do Task APIs
     */
    @GET("/todos/{todoId}/")
    void getToDo(@Header("Authorization") String token, @Path("todoId") String todoId, @Query("userId") String userId, Callback<ToDo> cb);

    @GET("/todos/all/")
    void getAllToDos(@Header("Authorization") String token, @Query("userId") String userId, Callback<ToDosList> cb);

    @POST("/todos/")
    void createToDo(@Header("Authorization") String token, @Query("userId") String userId, @Body ToDo todo, Callback<ToDo> cb);

    @POST("/todos/{todoId}/check/")
    void checkToDo(@Header("Authorization") String token, @Query("userId") String userId, @Path("todoId") String todoId, Callback<ToDo> cb);

    @POST("/todos/{todoId}/uncheck/")
    void uncheckToDo(@Header("Authorization") String token, @Query("userId") String userId, @Path("todoId") String todoId, Callback<ToDo> cb);

    @PUT("/todos/{todoId}/")
    void updateToDo(@Header("Authorization") String token, @Query("userId") String userId, @Path("todoId") String todoId, Callback<ToDo> cb);

    @DELETE("/todos/{todoId}/")
    void deleteToDo(@Header("Authorization") String token, @Query("userId") String userId, @Path("todoId") String todoId, Callback<DeletedItem> cb);

    /**
     *  Reward APIs
     */
    @GET("/rewards/{rewardId}")
    void getReward(@Header("Authorization") String token, @Path("rewardId") String rewardId, @Query("userId") String userId, Callback<Reward> cb);

    @GET("/rewards/all/")
    void getRewards(@Header("Authorization") String token, @Query("userId") String userId, Callback<RewardsList> cb);

    @POST("/rewards/")
    void createReward(@Header("Authorization") String token, @Query("userId") String userId, @Body Reward reward, Callback<Reward> cb);

    @POST("/rewards/buy/{rewardId}/")
    void buyReward(@Header("Authorization") String token, @Query("userId") String userId, @Path("rewardId") String rewardId, Callback<Reward> cb);

    @PUT("/rewards/{rewardId}/")
    void updateReward(@Header("Authorization") String token, @Query("userId") String userId, @Path("rewardId") String rewardId, Callback<Reward> cb);

    @DELETE("/rewards/{rewardId}/")
    void deleteReward(@Header("Authorization") String token, @Query("userId") String userId, @Path("rewardId") String rewardId, Callback<DeletedItem> cb);

}
