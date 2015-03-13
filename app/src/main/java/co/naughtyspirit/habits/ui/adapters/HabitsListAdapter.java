package co.naughtyspirit.habits.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;
import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.BusProvider;
import co.naughtyspirit.habits.bus.events.habit.CreateHabitEvent;
import co.naughtyspirit.habits.bus.events.habit.DeleteHabitEvent;
import co.naughtyspirit.habits.bus.events.habit.GetHabitsEvent;
import co.naughtyspirit.habits.bus.events.habit.IncrementHabitEvent;
import co.naughtyspirit.habits.bus.events.habit.UpdateHabitEvent;
import co.naughtyspirit.habits.bus.producers.HabitEventsProducer;
import co.naughtyspirit.habits.bus.producers.UserEventsProducer;
import co.naughtyspirit.habits.net.models.DeletedItem;
import co.naughtyspirit.habits.net.models.habit.Habit;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class HabitsListAdapter extends BaseAdapter {

    private Context ctx;
    private ArrayList<Habit> habits = new ArrayList<>();
    private LayoutInflater inflater;

    public HabitsListAdapter(Context ctx) {
        this.ctx = ctx;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        BusProvider.getInstance().register(this);
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        final Habit habit = ((Habit) getItem(position));

        if (view == null) {
            view = inflater.inflate(R.layout.layout_cell_habit, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag(); 
        }

        HabitOnClickListener listener = new HabitOnClickListener(habit);

        viewHolder.habitCell.setBackgroundColor(forState(habit.getStateAsInt()));
        viewHolder.habitName.setText(habit.getText());

        viewHolder.deleteHabit.setOnClickListener(null);
        viewHolder.deleteHabit.setOnClickListener(listener);

        viewHolder.incHabit.setOnClickListener(null);
        viewHolder.incHabit.setOnClickListener(listener);
        
        return view;
    }

    @Override
    public int getCount() {
        return habits.size();
    }

    @Override
    public Object getItem(int position) {
        return habits.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Subscribe
    public void onHabitsObtainSuccess(GetHabitsEvent event) {
        addItems(event.getList().getHabits());
    }

    @Subscribe
    public void onCreateHabitSuccess(CreateHabitEvent event) {
        addItem(event.getHabit());
    }

    @Subscribe
    public void onIncrementHabitSuccess(IncrementHabitEvent event) {
        updateItem(event.getHabit());
        UserEventsProducer.produceGetUserStatsEvent(ctx);
    }

    @Subscribe
    public void onUpdateHabitSuccess(UpdateHabitEvent event) {
        updateItem(event.getHabit());
    }

    @Subscribe
    public void onDeleteHabitSuccess(DeleteHabitEvent event) {
        deleteItem(event.getItem());
    }

    private int forState(int state) {
        if (state == 1) {
            return ctx.getResources().getColor(R.color.bg_medium);
        } else if (state == 2) {
            return ctx.getResources().getColor(R.color.bg_strong);
        } else {
            return ctx.getResources().getColor(R.color.bg_weak);
        }
    }

    private void addItems(ArrayList<Habit> habits) {
        this.habits.clear();
        this.habits.addAll(habits);
        notifyDataSetChanged();
    }

    private synchronized void addItem(Habit habit) {
        habits.add(0, habit);
        notifyDataSetChanged();
    }

    private synchronized void updateItem(Habit habit) {
        for (Habit h : habits) {
            if (h.getId().equals(habit.getId())) {
                h.setState(String.valueOf(habit.getState()));
                break;
            }
        }
        notifyDataSetChanged();
    }

    private synchronized void deleteItem(DeletedItem item) {
        ArrayList<Habit> copyList = new ArrayList<>();
        copyList.addAll(habits);

        for (Habit h : copyList) {
            if (h.getId().equals(item.getId())) {
                copyList.remove(h);
                break;
            }
        }

        if (copyList.size() != habits.size()) {
            habits.clear();
            habits.addAll(copyList);
            notifyDataSetChanged();
        }
    }
    
    static class ViewHolder {
        @InjectView(R.id.rl_cell_habit) RelativeLayout habitCell;
        @InjectView(R.id.tv_habit_name) TextView habitName;
        @InjectView(R.id.btn_delete_habit) Button deleteHabit;
        @InjectView(R.id.btn_increment_habit) Button incHabit;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    private class HabitOnClickListener implements OnClickListener {

        private final Habit habit;

        public HabitOnClickListener(Habit habit) {
            this.habit = habit;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_increment_habit:
                    HabitEventsProducer.produceIncrementHabitEvent(AuthProviderFactory.getProvider().getUser(),
                            habit);
                    break;

                case R.id.btn_delete_habit:
                    HabitEventsProducer.produceDeleteHabitEvent(AuthProviderFactory.getProvider().getUser(),
                            habit);
                    break;
            }
        }
    }
}
