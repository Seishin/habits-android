package co.naughtyspirit.habits.auth;

import android.app.Activity;
import android.content.Context;

import co.naughtyspirit.habits.net.models.user.User;
import co.naughtyspirit.habits.utils.Constants;
import co.naughtyspirit.habits.utils.SharedPreferencesUtil;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/27/15.
 * *
 * * NaughtySpirit 2015
 */
public class DefaultAuthProvider extends BaseAuthProvider {
    
    private static final String TAG = DefaultAuthProvider.class.getName();

    @Override
    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void login(User user) {
        super.login(user);
    }

    @Override
    public void logout() {
        super.logout();
    }

    @Override
    public boolean isUserLoggedIn() {
        return SharedPreferencesUtil.getStringPreference(activity, Constants.KEY_USER_ID) != null;
    }
}
