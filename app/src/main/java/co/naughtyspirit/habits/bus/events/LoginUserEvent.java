package co.naughtyspirit.habits.bus.events;

import co.naughtyspirit.habits.net.models.User;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/26/15.
 * *
 * * NaughtySpirit 2015
 */
public class LoginUserEvent {
    
    private User user;
    
    public LoginUserEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
