package co.naughtyspirit.habits.bus.events.ui;

import android.support.v4.app.Fragment;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/14/15.
 * *
 * * NaughtySpirit 2015
 */
public class ShowFragmentEvent {
    private Fragment fragment;
    private String tag;

    public ShowFragmentEvent(Fragment fragment, String tag) {
        this.fragment = fragment;
        this.tag = tag;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public String getTag() {
        return tag;
    }
}
