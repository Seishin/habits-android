package co.naughtyspirit.habits.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.auth.DefaultAuthProvider;
import co.naughtyspirit.habits.bus.producers.net.UserEventsProducer;
import co.naughtyspirit.habits.net.models.user.User;
import co.naughtyspirit.habits.ui.interfaces.OnViewPagerFragmentChange;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/22/15.
 * *
 * * NaughtySpirit 2015
 */
public class RegisterFragment extends BaseFragment {

    private static final String TAG = RegisterFragment.class.getName();

    private OnViewPagerFragmentChange callback;

    @InjectView(R.id.username) EditText name;
    @InjectView(R.id.email) EditText email;
    @InjectView(R.id.password) EditText password;
    @InjectView(R.id.btn_submit) Button submit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.inject(this, view);

        initUI();
        
        return view;
    }

    private void initUI() {
        name.setTypeface(getHelveticaLight());
        email.setTypeface(getHelveticaLight());
        password.setTypeface(getHelveticaLight());
        submit.setTypeface(getHelveticaLight());
    }

    @OnClick(R.id.btn_submit)
    public void createProfile() {
        User user = new User();
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());
        user.setName(name.getText().toString());

        UserEventsProducer.produceUserCreatedEvent(activity, DefaultAuthProvider.class.getCanonicalName(), user);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            this.callback = (OnViewPagerFragmentChange) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, e.getMessage());
        }
    }
}
