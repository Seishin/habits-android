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
import co.naughtyspirit.habits.bus.events.todo.CheckToDoEvent;
import co.naughtyspirit.habits.bus.events.todo.CreateToDoEvent;
import co.naughtyspirit.habits.bus.events.todo.DeleteToDoEvent;
import co.naughtyspirit.habits.bus.events.todo.GetToDosEvent;
import co.naughtyspirit.habits.bus.events.todo.UncheckToDoEvent;
import co.naughtyspirit.habits.bus.events.todo.UpdateToDoEvent;
import co.naughtyspirit.habits.bus.producers.ToDoEventsProducer;
import co.naughtyspirit.habits.bus.producers.UserEventsProducer;
import co.naughtyspirit.habits.net.models.DeletedItem;
import co.naughtyspirit.habits.net.models.todo.ToDo;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class ToDosListAdapter extends BaseAdapter {

    private static final String TAG = ToDosListAdapter.class.getName();

    private Context ctx;
    private ArrayList<ToDo> todos = new ArrayList<>();
    private LayoutInflater inflater;

    public ToDosListAdapter(Context ctx) {
        this.ctx = ctx;
        this.inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        BusProvider.getInstance().register(this);
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder viewHolder;

        final ToDo todo = ((ToDo) getItem(position));

        if (view == null) {
            view = inflater.inflate(R.layout.layout_cell_todo, parent, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag(); 
        }

        viewHolder.todoCell.setBackgroundColor(forState(todo.getStateAsInt()));
        viewHolder.todoName.setText(todo.getText());

        viewHolder.todoDelete.setOnClickListener(null);
        viewHolder.todoDelete.setOnClickListener(new CustomOnClickListener(todo));

        viewHolder.todoCheck.setChecked(todo.getStateAsBoolean());
        viewHolder.todoCheck.setOnClickListener(null);
        viewHolder.todoCheck.setOnClickListener(new CustomOnClickListener(todo));

        return view;
    }

    @Override
    public int getCount() {
        return todos.size();
    }

    @Override
    public Object getItem(int position) {
        return todos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Subscribe
    public void onToDosObtainSuccess(GetToDosEvent event) {
        addItems(event.getList().getTodos());
    }

    @Subscribe
    public void onCreateToDoSuccess(CreateToDoEvent event) {
        addItem(event.getToDo());
    }

    @Subscribe
    public void onCheckToDoSuccess(CheckToDoEvent event) {
        updateItem(event.getToDo());
        UserEventsProducer.produceGetUserStatsEvent(ctx);
    }
    
    @Subscribe
    public void onUnCheckToDoSuccess(UncheckToDoEvent event) {
        updateItem(event.getToDo());
        UserEventsProducer.produceGetUserStatsEvent(ctx);
    }

    @Subscribe
    public void onUpdateToDoSuccess(UpdateToDoEvent event) {
        updateItem(event.getToDo());
    }

    @Subscribe
    public void onDeleteToDoSuccess(DeleteToDoEvent event) {
        deleteItem(event.getItem());
    }

    private int forState(int state) {
        return (state == 0) ? ctx.getResources().getColor(R.color.bg_weak) : ctx.getResources().getColor(R.color.bg_strong);
    }

    private void addItems(ArrayList<ToDo> todos) {
        this.todos.addAll(todos);
        notifyDataSetChanged();
    }

    private synchronized void addItem(ToDo task) {
        todos.add(0, task);
        notifyDataSetChanged();
    }

    private synchronized void updateItem(ToDo todo) {
        for (ToDo t : todos) {
            if (t.getId().equals(todo.getId())) {
                t.setState(todo.getState());
                break;
            }
        }
        notifyDataSetChanged();
    }

    private synchronized void deleteItem(DeletedItem item) {
        ArrayList<ToDo> copyList = new ArrayList<>();
        copyList.addAll(todos);

        for (ToDo t : copyList) {
            if (t.getId().equals(item.getId())) {
                copyList.remove(t);
                break;
            }
        }

        if (copyList.size() != todos.size()) {
            todos.clear();
            todos.addAll(copyList);
            notifyDataSetChanged();
        }
    }
    
    static class ViewHolder {
        @InjectView(R.id.rl_todo_cell) RelativeLayout todoCell;
        @InjectView(R.id.tv_todo_name) TextView todoName;
        @InjectView(R.id.btn_delete_todo) Button todoDelete;
        @InjectView(R.id.cb_check_todo) CheckBox todoCheck;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    private class CustomOnClickListener implements View.OnClickListener {

        private final ToDo todo;

        public CustomOnClickListener(ToDo todo) {
            this.todo = todo;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.cb_check_todo:
                    if (todo.getStateAsBoolean()) {
                        ToDoEventsProducer.produceUnCheckToDoEvent(AuthProviderFactory.getProvider().getUser(), todo);
                    } else {
                        ToDoEventsProducer.produceCheckToDoEvent(AuthProviderFactory.getProvider().getUser(), todo);
                    }
                    break;

                case R.id.btn_delete_todo:
                    ToDoEventsProducer.produceDeleteToDoEvent(AuthProviderFactory.getProvider().getUser(), todo);
                    break;
            }
        }
    }
}
