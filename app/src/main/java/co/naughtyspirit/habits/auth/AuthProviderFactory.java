package co.naughtyspirit.habits.auth;

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
    private static Context ctx;

    public static AuthProvider getProvider() {
        if (provider == null) {
            try {
                provider = (AuthProvider) Class.forName(SharedPreferencesUtil.getStringPreference(ctx,
                        Constants.KEY_LOGIN_PROVIDER)).newInstance();
                provider.setContext(ctx);
            } catch (Exception e) {
                Log.e(TAG, "Cannot get instance of the auth provider.");
            }
        }
        
        return provider;
    }
    
    public static void onCreate(Context ctx) {
        AuthProviderFactory.ctx = ctx;

        setAuthProvider(DefaultAuthProvider.class.getCanonicalName());
    }
    
    public static void setAuthProvider(String provider) {
        SharedPreferencesUtil.setPreference(ctx, Constants.KEY_LOGIN_PROVIDER, provider);
    }
}
