package co.naughtyspirit.habits.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.CreateHabitEvent;
import co.naughtyspirit.habits.bus.events.GetHabitsEvent;
import co.naughtyspirit.habits.bus.events.GetUserStatsEvent;
import co.naughtyspirit.habits.bus.events.HabitsFailureEvent;
import co.naughtyspirit.habits.bus.events.IncrementHabitEvent;
import co.naughtyspirit.habits.bus.producers.HabitEventsProducer;
import co.naughtyspirit.habits.bus.producers.UserEventsProducer;
import co.naughtyspirit.habits.net.models.Habit;
import co.naughtyspirit.habits.views.adapters.HabitsListAdapter;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class HabitsFragment extends Fragment implements OnClickListener {

    private static final String TAG = HabitsFragment.class.getName();
    
    private Activity activity;
    
    private View view;
    private ListView habitsList;
    private HabitsListAdapter habitsListAdapter;
    private EditText createHabitText;
    private Button createHabitSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_habits, container, false);
        
        initUI();

        HabitEventsProducer.produceGetHabitsEvent(AuthProviderFactory.getProvider().getUser());
        
        return view;
    }

    private void initUI() {
        createHabitText = (EditText) view.findViewById(R.id.text);
        createHabitSubmit = (Button) view.findViewById(R.id.submit);
        createHabitSubmit.setOnClickListener(this);
        
        habitsList = (ListView) view.findViewById(R.id.list_habits);
        habitsListAdapter = new HabitsListAdapter(activity);
        habitsList.setAdapter(habitsListAdapter);
    }
    
    @Subscribe
    public void onHabitsObtainSuccess(GetHabitsEvent event) {
        habitsListAdapter.addItems(event.getList().getHabits());
    }

    @Subscribe
    public void onHabitsFailure(HabitsFailureEvent event) {
        Toast.makeText(activity, event.getMessage(), Toast.LENGTH_LONG).show();
    }
    
    @Subscribe
    public void onCreateHabitSuccess(CreateHabitEvent event) {
        habitsListAdapter.addItem(event.getHabit());
    }
    
    @Subscribe
    public void onIncrementHabitSuccess(IncrementHabitEvent event) {
        UserEventsProducer.produceGetUserStatsEvent(activity);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                HabitEventsProducer.produceCreateHabitEvent(AuthProviderFactory.getProvider().getUser(), 
                        new Habit(createHabitText.getText().toString()));
                break;
        }
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
