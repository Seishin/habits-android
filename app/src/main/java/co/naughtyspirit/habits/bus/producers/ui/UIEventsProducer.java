package co.naughtyspirit.habits.bus.producers.ui;

import android.support.v4.app.Fragment;

import com.squareup.otto.Produce;

import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.ui.ShowFragmentEvent;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/14/15.
 * *
 * * NaughtySpirit 2015
 */
public class UIEventsProducer {

    private static final String TAG = UIEventsProducer.class.getName();

    @Produce
    public static void produceFragmentEvent(Fragment fragment, String tag) {
        BusProvider.getInstance().post(new ShowFragmentEvent(fragment, tag));
    }

}
