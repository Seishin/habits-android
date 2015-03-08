package co.naughtyspirit.habits.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.DefaultAuthProvider;
import co.naughtyspirit.habits.bus.producers.UserEventsProducer;
import co.naughtyspirit.habits.net.models.user.User;
import co.naughtyspirit.habits.views.interfaces.OnViewPagerFragmentChange;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/22/15.
 * *
 * * NaughtySpirit 2015
 */
public class RegisterFragment extends Fragment implements OnClickListener{

    private static final String TAG = RegisterFragment.class.getName();

    private View view;
    private Activity activity;

    private OnViewPagerFragmentChange callback;
    private EditText name;
    private EditText email;
    private EditText password;
    private Button submit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);
        
        initUI();
        
        return view;
    }

    private void initUI() {
        name = (EditText) view.findViewById(R.id.username);
        email = (EditText) view.findViewById(R.id.email);
        password = (EditText) view.findViewById(R.id.password);
        
        submit = (Button) view.findViewById(R.id.btn_submit);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_submit:
                User user = new User();
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());
                user.setName(name.getText().toString());

                UserEventsProducer.produceUserCreatedEvent(activity, DefaultAuthProvider.class.getCanonicalName(), user);
                break;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        this.activity = activity;
        
        try {
            this.callback = (OnViewPagerFragmentChange) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
