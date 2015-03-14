package co.naughtyspirit.habits.auth;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import co.naughtyspirit.habits.utils.Constants;
import co.naughtyspirit.habits.utils.SharedPreferencesUtil;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/27/15.
 * *
 * * NaughtySpirit 2015
 */
public class AuthProviderFactory {
    private static final String TAG = AuthProviderFactory.class.getName();
    
    private static AuthProvider provider;

    public static AuthProvider getProvider(Context ctx) {
        return getProvider((Activity) ctx);
    }

    public static AuthProvider getProvider(Activity activity) {
        if (provider == null) {
            try {
                provider = (AuthProvider) Class.forName(SharedPreferencesUtil.getStringPreference(activity,
                        Constants.KEY_LOGIN_PROVIDER)).newInstance();
            } catch (Exception e) {
                Log.e(TAG, "Cannot get instance of the auth provider.");
            }
        }

        provider.setActivity(activity);
        return provider;
    }

    public static void setAuthProvider(Context ctx, String provider) {
        SharedPreferencesUtil.setPreference(ctx, Constants.KEY_LOGIN_PROVIDER, provider);
    }
}
