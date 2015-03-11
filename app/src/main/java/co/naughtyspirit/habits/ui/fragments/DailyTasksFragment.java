package co.naughtyspirit.habits.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.daily_tasks.DailyTasksFailureEvent;
import co.naughtyspirit.habits.bus.producers.DailyTaskEventsProducer;
import co.naughtyspirit.habits.net.models.daily_task.DailyTask;
import co.naughtyspirit.habits.net.models.user.User;
import co.naughtyspirit.habits.utils.WindowUtils;
import co.naughtyspirit.habits.ui.adapters.DailyTasksListAdapter;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class DailyTasksFragment extends BaseFragment {

    private static final String TAG = DailyTasksFragment.class.getName();
    
    private Activity activity;
    
    @InjectView(R.id.lv_todos) ListView dailyTasksList;
    @InjectView(R.id.et_todo_text) EditText taskText;
    
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_daily_tasks, container, false);
        ButterKnife.inject(this, view);

        DailyTaskEventsProducer.produceGetTasksEvent(AuthProviderFactory.getProvider().getUser());
        
        initUI();
        
        return view;
    }

    private void initUI() {
        dailyTasksList.setAdapter(new DailyTasksListAdapter(activity));
    }
    

    @OnClick(R.id.btn_submit)
    public void submitTask() {
        User user = AuthProviderFactory.getProvider().getUser();
        DailyTaskEventsProducer.produceCreateTaskEvent(user, new DailyTask(taskText.getText().toString()));

        taskText.getText().clear();
        WindowUtils.hideSoftKeyboard(activity, taskText);
    }
    
    @Subscribe
    public void onTasksObtainFailure(DailyTasksFailureEvent event) {
        taskText.setError(event.getMessage());
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