package co.naughtyspirit.habits.views.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import co.naughtyspirit.habits.R;
import co.naughtyspirit.habits.utils.Constants;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/22/15.
 * *
 * * NaughtySpirit 2015
 */
public class RegisterFragment extends Fragment implements OnClickListener{

    private View view;
    private Activity activity;
    private Button back;

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
        back = (Button) view.findViewById(R.id.back);
        back.setOnClickListener(this);
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(activity, R.anim.slide_in_left);
        } else {
            return AnimationUtils.loadAnimation(activity, R.anim.slide_out_right);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                ((FragmentActivity) activity).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container, new LoginFragment(), Constants.TAG_FRAGMENT_LOGIN)
                        .setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left)
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
