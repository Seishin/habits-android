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

    public static AuthProvider getProvider(Context ctx) {
        if (provider == null) {
            try {
                if (SharedPreferencesUtil.getStringPreference(ctx, Constants.KEY_LOGIN_PROVIDER) != null) {
                    provider = (AuthProvider) Class.forName(SharedPreferencesUtil.getStringPreference(ctx,
                            Constants.KEY_LOGIN_PROVIDER)).newInstance();
                    provider.setContext(ctx);

                } else {
                    provider = new DefaultAuthProvider();
                    provider.setContext(ctx);
                }
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
        
        return provider;
    }
}
