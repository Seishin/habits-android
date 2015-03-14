package co.naughtyspirit.habits.auth;

import android.app.Activity;
import android.content.Intent;

import co.naughtyspirit.habits.net.models.user.User;
import co.naughtyspirit.habits.ui.activities.AuthActivity;
import co.naughtyspirit.habits.ui.activities.MainActivity;
import co.naughtyspirit.habits.utils.Constants;
import co.naughtyspirit.habits.utils.SharedPreferencesUtil;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/14/15.
 * *
 * * NaughtySpirit 2015
 */
public abstract class BaseAuthProvider implements AuthProvider {

    protected Activity activity;

    @Override
    public void login(User user) {
        SharedPreferencesUtil.setPreference(activity, Constants.KEY_AUTH_TOKEN, user.getToken());
        SharedPreferencesUtil.setPreference(activity, Constants.KEY_USER_ID, user.getId());
        SharedPreferencesUtil.setPreference(activity, Constants.KEY_USER_NAME, user.getName());
        SharedPreferencesUtil.setPreference(activity, Constants.KEY_USER_EMAIL, user.getEmail());

        Intent i = new Intent(activity, MainActivity.class);
        activity.startActivity(i);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        activity.finish();
    }

    @Override
    public void logout() {
        SharedPreferencesUtil.removePreference(activity, Constants.KEY_LOGIN_PROVIDER);
        SharedPreferencesUtil.removePreference(activity, Constants.KEY_AUTH_TOKEN);
        SharedPreferencesUtil.removePreference(activity, Constants.KEY_USER_ID);
        SharedPreferencesUtil.removePreference(activity, Constants.KEY_USER_NAME);
        SharedPreferencesUtil.removePreference(activity, Constants.KEY_USER_EMAIL);

        Intent i = new Intent(activity, AuthActivity.class);
        activity.startActivity(i);
        activity.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        activity.finish();
    }

    @Override
    public User getUser() {
        User user = new User();
        user.setId(SharedPreferencesUtil.getStringPreference(activity, Constants.KEY_USER_ID));
        user.setToken(SharedPreferencesUtil.getStringPreference(activity, Constants.KEY_AUTH_TOKEN));
        user.setEmail(SharedPreferencesUtil.getStringPreference(activity, Constants.KEY_USER_EMAIL));
        user.setName(SharedPreferencesUtil.getStringPreference(activity, Constants.KEY_USER_NAME));

        return user;
    }
}
