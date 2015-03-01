package co.naughtyspirit.habits.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.AuthProviderFactory;
import co.naughtyspirit.habits.bus.producers.HabitEventsProducer;
import co.naughtyspirit.habits.net.models.Habit;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class HabitsListAdapter extends BaseAdapter {

    private final Context ctx;
    private final ArrayList<Habit> habits = new ArrayList<>();
    private final LayoutInflater inflater;
    private ViewHolder viewHolder;

    public HabitsListAdapter(Context ctx) {
        this.ctx = ctx;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        
        if (view == null) {
            view = inflater.inflate(R.layout.layout_cell_habit, parent, false);
            
            viewHolder = new ViewHolder();
            viewHolder.text = (TextView) view.findViewById(R.id.text);
            viewHolder.increment = (Button) view.findViewById(R.id.increment);
            
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag(); 
        }
        
        viewHolder.text.setText(((Habit) getItem(position)).getText());
        viewHolder.increment.setOnClickListener(null);
        viewHolder.increment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HabitEventsProducer.produceIncrementHabitEvent(AuthProviderFactory.getProvider().getUser(),
                        (Habit) getItem(position));
            }
        });
        
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
    
    public void addItem(Habit habit) {
        habits.add(0, habit);
        notifyDataSetChanged();
    }
    
    public void addItems(ArrayList<Habit> habits) {
        this.habits.clear();
        this.habits.addAll(habits);
        notifyDataSetChanged();
    }
    
    static class ViewHolder {
        TextView text;
        Button increment;
    }
}
