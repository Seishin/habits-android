package co.naughtyspirit.habits.bus.events;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/28/15.
 * *
 * * NaughtySpirit 2015
 */
public class AuthFailureEvent extends BaseFailureEvent {
    public AuthFailureEvent(String message) {
        this.message = message;
    }
}
