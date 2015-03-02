package co.naughtyspirit.habits.bus.events.users;

import co.naughtyspirit.habits.net.models.User;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/22/15.
 * *
 * * NaughtySpirit 2015
 */
public class CreateUserEvent {

    private User user;

    public CreateUserEvent(User user) {
        this.user = user;
    }
    
    public User getUser() {
        return user;
    }
}
