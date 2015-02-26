package co.naughtyspirit.habits.net;

import co.naughtyspirit.habits.utils.Constants;
import retrofit.RestAdapter;

/**
 * Created by Seishin <atanas@naughtyspirit.co>
 * on 2/22/15.
 *
 * NaughtySpirit 2015
 */
public class HabitsApiClient {
    
    private static HabitsApi client;
    
    public static HabitsApi getClient() {
        if (client == null) {
            RestAdapter adapter = new RestAdapter.Builder()
                    .setEndpoint(Constants.API_ENDPOINT)
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .build();
            
            client = adapter.create(HabitsApi.class);
        }
        
        return client;
    }
}
