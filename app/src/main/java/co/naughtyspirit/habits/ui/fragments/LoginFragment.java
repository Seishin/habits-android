package co.naughtyspirit.habits.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

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
public class LoginFragment extends BaseFragment {

    private static final String TAG = LoginFragment.class.getName();
    
    @InjectView(R.id.email) EditText email;
    @InjectView(R.id.password) EditText password;
    @InjectView(R.id.register) TextView register;

    private OnViewPagerFragmentChange callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        ButterKnife.inject(this, view);
        
        initUI();
        
        return view;
    }
    
    private void initUI() {
        email.setTypeface(getHelveticaLight());
        password.setTypeface(getHelveticaLight());
        register.setTypeface(getHelveticaLight());
    }

    @OnClick(R.id.btn_submit)
    public void submit() {
        User user = new User();
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());

        UserEventsProducer.produceUserLoginEvent(activity, DefaultAuthProvider.class.getCanonicalName(), user);
    }

    @OnClick(R.id.register)
    public void showRegistrationFragment() {
        callback.setFragmentAt(1);
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
