package co.naughtyspirit.habits.views.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;

import co.naughtyspirit.habits.MainActivity;
import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.bus.producers.UserEventsProducer;
import co.naughtyspirit.habits.net.models.User;
import co.naughtyspirit.habits.utils.Constants;
import co.naughtyspirit.habits.utils.FontsLoaderUtil;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/22/15.
 * *
 * * NaughtySpirit 2015
 */
public class LoginFragment extends Fragment implements OnClickListener{

    private static final String TAG = LoginFragment.class.getName();
    private View view;
    private EditText email;
    private EditText password;
    private Button submit;
    private Button register;
    
    private Activity activity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_login, container, false);
        
        initUI();
        
        return view;
    }
    
    private void initUI() {
        Typeface helveticaLight = FontsLoaderUtil.getHelveticaNeueLight(activity);

        email = (EditText) view.findViewById(R.id.email);
        email.setTypeface(helveticaLight);

        password = (EditText) view.findViewById(R.id.password);
        password.setTypeface(helveticaLight);

        submit = (Button) view.findViewById(R.id.submit);
        submit.setOnClickListener(this);

        register = (Button) view.findViewById(R.id.register);
        register.setTypeface(helveticaLight);
        register.setOnClickListener(this);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (!enter) {
            return AnimationUtils.loadAnimation(activity, R.anim.slide_out_left);
        }

        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.submit:
                Log.i(TAG, "Clicked");
                
                User user = new User();
                user.setEmail(email.getText().toString());
                user.setPassword(password.getText().toString());

                UserEventsProducer.produceUserCreatedEvent(user);

                break;
            
            case R.id.register:
                ((MainActivity) activity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new RegisterFragment(), Constants.TAG_FRAGMENT_REGISTER)
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_right)
                        .commit();
                break;
        }
    }
    
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        
        this.activity = activity;
    }
}
