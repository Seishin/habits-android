package co.naughtyspirit.habits.auth;

import android.content.Context;

import co.naughtyspirit.habits.net.models.User;
import co.naughtyspirit.habits.utils.Constants;
import co.naughtyspirit.habits.utils.SharedPreferencesUtil;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/27/15.
 * *
 * * NaughtySpirit 2015
 */
public class DefaultAuthProvider implements AuthProvider {
    
    private static final String TAG = DefaultAuthProvider.class.getName();

    private Context ctx;

    @Override
    public void setContext(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    public boolean login(User user) {
        if (user != null) {
            SharedPreferencesUtil.setPreference(ctx, Constants.KEY_AUTH_TOKEN, user.getToken());
            SharedPreferencesUtil.setPreference(ctx, Constants.KEY_USER_ID, user.getId());
            SharedPreferencesUtil.setPreference(ctx, Constants.KEY_USER_NAME, user.getName());
            SharedPreferencesUtil.setPreference(ctx, Constants.KEY_USER_EMAIL, user.getEmail());
            
            return true;
        }
        
        return false;
    }

    @Override
    public void logout() {
        SharedPreferencesUtil.removePreference(ctx, Constants.KEY_LOGIN_PROVIDER);
        SharedPreferencesUtil.removePreference(ctx, Constants.KEY_AUTH_TOKEN);
        SharedPreferencesUtil.removePreference(ctx, Constants.KEY_USER_ID);
        SharedPreferencesUtil.removePreference(ctx, Constants.KEY_USER_NAME);
        SharedPreferencesUtil.removePreference(ctx, Constants.KEY_USER_EMAIL);
    }

    @Override
    public boolean isUserLoggedIn() {
        return SharedPreferencesUtil.getStringPreference(ctx, Constants.KEY_USER_ID) != null;
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setId(SharedPreferencesUtil.getStringPreference(ctx, Constants.KEY_USER_ID));
        user.setToken(SharedPreferencesUtil.getStringPreference(ctx, Constants.KEY_AUTH_TOKEN));
        user.setEmail(SharedPreferencesUtil.getStringPreference(ctx, Constants.KEY_USER_EMAIL));
        user.setName(SharedPreferencesUtil.getStringPreference(ctx, Constants.KEY_USER_NAME));

        return user;
    }
}
