package co.naughtyspirit.habits.bus;

import com.squareup.otto.Bus;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/22/15.
 * *
 * * NaughtySpirit 2015
 */
public final class BusProvider {
    
    private static Bus instance;
    
    public static Bus getInstance() {
        if (instance == null) {
            instance = new Bus();
        }
        
        return instance;
    }
}
