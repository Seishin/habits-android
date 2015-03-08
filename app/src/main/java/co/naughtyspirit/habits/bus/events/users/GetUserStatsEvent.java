package co.naughtyspirit.habits.bus.events.users;

import co.naughtyspirit.habits.net.models.user.UserStats;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/27/15.
 * *
 * * NaughtySpirit 2015
 */
public class GetUserStatsEvent {
    
    private UserStats stats;
    
    public GetUserStatsEvent(UserStats stats) {
        this.stats = stats;
    }

    public UserStats getStats() {
        return stats;
    }
}
