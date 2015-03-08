package co.naughtyspirit.habits.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.daily_tasks.CheckDailyTaskEvent;
import co.naughtyspirit.habits.bus.events.daily_tasks.CreateDailyTaskEvent;
import co.naughtyspirit.habits.bus.events.daily_tasks.DeleteDailyTaskEvent;
import co.naughtyspirit.habits.bus.events.daily_tasks.GetDailyTasksEvent;
import co.naughtyspirit.habits.bus.events.daily_tasks.UncheckDailyTaskEvent;
import co.naughtyspirit.habits.bus.events.daily_tasks.UpdateDailyTaskEvent;
import co.naughtyspirit.habits.bus.producers.DailyTaskEventsProducer;
import co.naughtyspirit.habits.bus.producers.UserEventsProducer;
import co.naughtyspirit.habits.net.models.DeletedItem;
import co.naughtyspirit.habits.net.models.daily_task.DailyTask;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class DailyTasksListAdapter extends BaseAdapter {

    private static final String TAG = DailyTasksListAdapter.class.getName();

    private Context ctx;
    private ArrayList<DailyTask> tasks = new ArrayList<>();
    private LayoutInflater inflater;

    public DailyTasksListAdapter(Context ctx) {
        this.ctx = ctx;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        BusProvider.getInstance().register(this);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        final DailyTask task = ((DailyTask) getItem(position));

        if (view == null) {
            view = inflater.inflate(R.layout.layout_cell_daily_task, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag(); 
        }

        viewHolder.taskCell.setBackgroundColor(forState(task.getStateAsInt()));
        viewHolder.taskName.setText(task.getText());

        viewHolder.taskDelete.setOnClickListener(null);
        viewHolder.taskDelete.setOnClickListener(new CustomOnClickListener(task));

        viewHolder.taskCheck.setChecked(task.getStateAsBoolean());
        viewHolder.taskCheck.setOnClickListener(null);
        viewHolder.taskCheck.setOnClickListener(new CustomOnClickListener(task));

        return view;
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Subscribe
    public void onTasksObtainSuccess(GetDailyTasksEvent event) {
        addItems(event.getList().getTasks());
    }

    @Subscribe
    public void onCreateTaskSuccess(CreateDailyTaskEvent event) {
        addItem(event.getTask());
    }

    @Subscribe
    public void onCheckTaskSuccess(CheckDailyTaskEvent event) {
        updateItem(event.getTask());
        UserEventsProducer.produceGetUserStatsEvent(ctx);
    }
    
    @Subscribe
    public void onUnCheckTaskSuccess(UncheckDailyTaskEvent event) {
        updateItem(event.getTask());
        UserEventsProducer.produceGetUserStatsEvent(ctx);
    }

    @Subscribe
    public void onUpdateTaskSuccess(UpdateDailyTaskEvent event) {
        updateItem(event.getTask());
    }

    @Subscribe
    public void onDeleteTaskSuccess(DeleteDailyTaskEvent event) {
        deleteItem(event.getItem());
    }

    private int forState(int state) {
        return (state == 0) ? ctx.getResources().getColor(R.color.bg_weak) : ctx.getResources().getColor(R.color.bg_strong);
    }

    private void addItems(ArrayList<DailyTask> tasks) {
        this.tasks.addAll(tasks);
        notifyDataSetChanged();
    }

    private synchronized void addItem(DailyTask task) {
        tasks.add(0, task);
        notifyDataSetChanged();
    }

    private synchronized void updateItem(DailyTask task) {
        for (DailyTask t : tasks) {
            if (t.getId().equals(task.getId())) {
                t.setState(task.getState());
                break;
            }
        }
        notifyDataSetChanged();
    }

    private synchronized void deleteItem(DeletedItem item) {
        ArrayList<DailyTask> copyList = new ArrayList<>();
        copyList.addAll(tasks);

        for (DailyTask t : copyList) {
            if (t.getId().equals(item.getId())) {
                copyList.remove(t);
                break;
            }
        }

        if (copyList.size() != tasks.size()) {
            tasks.clear();
            tasks.addAll(copyList);
            notifyDataSetChanged();
        }
    }
    
    static class ViewHolder {
        @InjectView(R.id.rl_task_cell) RelativeLayout taskCell;
        @InjectView(R.id.tv_task_name) TextView taskName;
        @InjectView(R.id.btn_delete_task) Button taskDelete;
        @InjectView(R.id.cb_check_task) CheckBox taskCheck;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    private class CustomOnClickListener implements View.OnClickListener {

        private final DailyTask task;

        public CustomOnClickListener(DailyTask task) {
            this.task = task;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cb_check_task:
                    if (task.getStateAsBoolean()) {
                        DailyTaskEventsProducer.produceUnCheckTaskEvent(AuthProviderFactory.getProvider().getUser(), task);
                    } else {
                        DailyTaskEventsProducer.produceCheckTaskEvent(AuthProviderFactory.getProvider().getUser(), task);
                    }
                    break;

                case R.id.btn_delete_task:
                    DailyTaskEventsProducer.produceDeleteTaskEvent(AuthProviderFactory.getProvider().getUser(), task);
                    break;
            }
        }
    }
}
