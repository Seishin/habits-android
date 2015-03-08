package co.naughtyspirit.habits;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import co.naughtyspirit.habits.auth.AuthProviderFactory;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        AuthProviderFactory.onCreate(this);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent;

                if (AuthProviderFactory.getProvider().isUserLoggedIn()) {
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    intent = new Intent(SplashActivity.this, AuthActivity.class);
                }

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }, 3000);
    }
}
