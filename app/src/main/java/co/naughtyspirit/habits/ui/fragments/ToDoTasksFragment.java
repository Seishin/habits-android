package co.naughtyspirit.habits.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import co.naughtyspirit.habits.bus.events.todo.ToDoFailureEvent;
import co.naughtyspirit.habits.bus.producers.ToDoEventsProducer;
import co.naughtyspirit.habits.net.models.todo.ToDo;
import co.naughtyspirit.habits.net.models.user.User;
import co.naughtyspirit.habits.ui.adapters.ToDosListAdapter;
import co.naughtyspirit.habits.utils.WindowUtils;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class ToDoTasksFragment extends Fragment {

    private static final String TAG = ToDoTasksFragment.class.getName();

    private Activity activity;

    @InjectView(R.id.lv_todos) ListView todosList;
    @InjectView(R.id.et_todo_text) EditText todoText;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_todo_tasks, container, false);
        ButterKnife.inject(this, view);

        ToDoEventsProducer.produceGetToDosEvent(AuthProviderFactory.getProvider().getUser());

        initUI();

        return view;
    }

    private void initUI() {
        todosList.setAdapter(new ToDosListAdapter(activity));
    }


    @OnClick(R.id.btn_submit)
    public void submitTask() {
        User user = AuthProviderFactory.getProvider().getUser();
        ToDoEventsProducer.produceCreateToDoEvent(user, new ToDo(todoText.getText().toString()));

        todoText.getText().clear();
        WindowUtils.hideSoftKeyboard(activity, todoText);
    }

    @Subscribe
    public void onToDosObtainFailure(ToDoFailureEvent event) {
        todoText.setError(event.getMessage());
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
