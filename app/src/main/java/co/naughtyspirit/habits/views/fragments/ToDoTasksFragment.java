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

import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.bus.BusProvider;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class ToDoTasksFragment extends Fragment implements OnClickListener {

    private static final String TAG = ToDoTasksFragment.class.getName();
    
    private Activity activity;
    
    private View view;
    private ListView toDoTasksList;
//    private HabitsListAdapter habitsListAdapter;
    private EditText createHabitText;
    private Button createHabitSubmit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_todo_tasks, container, false);
        
//        initUI();

//        HabitEventsProducer.produceGetHabitsEvent(AuthProviderFactory.getProvider().getUser());
        
        return view;
    }

    private void initUI() {
//        habitText = (EditText) view.findViewById(R.id.text);
//        createHabitSubmit = (Button) view.findViewById(R.id.submit);
//        createHabitSubmit.setOnClickListener(this);
//
//        toDoTasksList = (ListView) view.findViewById(R.id.list_habits);
    }
    

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                
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
