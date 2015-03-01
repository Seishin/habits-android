package co.naughtyspirit.habits.auth;

import android.content.Context;

import co.naughtyspirit.habits.net.models.User;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/27/15.
 * *
 * * NaughtySpirit 2015
 */
public interface AuthProvider {
    
    public void setContext(Context ctx);
    
    public boolean login(User user);
    public void logout();
    public boolean isUserLoggedIn();
 
    public User getUser();
}
