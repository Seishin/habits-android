package co.naughtyspirit.habits.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.net.habit.HabitsFailureEvent;
import co.naughtyspirit.habits.bus.producers.net.HabitEventsProducer;
import co.naughtyspirit.habits.net.models.habit.Habit;
import co.naughtyspirit.habits.ui.adapters.HabitsListAdapter;
import co.naughtyspirit.habits.utils.WindowUtils;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class HabitsFragment extends BaseFragment {

    private static final String TAG = HabitsFragment.class.getName();

    @InjectView(R.id.header) TextView header;
    @InjectView(R.id.lv_habits) ListView habitsList;
    @InjectView(R.id.et_habit_text) EditText habitText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_habits, container, false);
        ButterKnife.inject(this, view);

        HabitEventsProducer.produceGetHabitsEvent(AuthProviderFactory.getProvider(activity).getUser());

        initUI();

        return view;
    }

    private void initUI() {
        header.setTypeface(getHelvetica());
        habitText.setTypeface(getHelveticaLight());
        habitsList.setAdapter(new HabitsListAdapter(activity));
    }

    @OnClick(R.id.btn_submit)
    public void submitHabit() {
        HabitEventsProducer.produceCreateHabitEvent(AuthProviderFactory.getProvider(activity).getUser(),
                new Habit(habitText.getText().toString()));

        habitText.getText().clear();
        WindowUtils.hideSoftKeyboard(activity, habitText);
    }

    @Subscribe
    public void onHabitsFailure(HabitsFailureEvent event) {
        Toast.makeText(activity, event.getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }

    @Override
    public void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }
}