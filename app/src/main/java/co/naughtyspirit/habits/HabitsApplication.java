package co.naughtyspirit.habits;

import android.app.Application;

import co.naughtyspirit.habits.auth.DefaultAuthProvider;
import co.naughtyspirit.habits.utils.Constants;
import co.naughtyspirit.habits.utils.SharedPreferencesUtil;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/14/15.
 * *
 * * NaughtySpirit 2015
 */
public class HabitsApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        setDefAuthProviderIfNoneIsSet();
    }


    private void setDefAuthProviderIfNoneIsSet() {
        if (SharedPreferencesUtil.getStringPreference(this, Constants.KEY_LOGIN_PROVIDER) == null) {
            SharedPreferencesUtil.setPreference(this, Constants.KEY_LOGIN_PROVIDER,
                    DefaultAuthProvider.class.getCanonicalName());
        }
    }
}
