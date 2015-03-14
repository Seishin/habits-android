package co.naughtyspirit.habits.ui.fragments;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import co.naughtyspirit.habits.utils.FontsLoaderUtil;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/8/15.
 * *
 * * NaughtySpirit 2015
 */
public abstract class BaseFragment extends Fragment {

    protected Activity activity;
    protected Typeface helveticaLight;
    protected Typeface helvetica;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.helveticaLight = FontsLoaderUtil.getHelveticaNeueLight(activity);
        this.helvetica = FontsLoaderUtil.getHelveticaNeue(activity);
    }

    protected Typeface getHelveticaLight() {
        return helveticaLight;
    }
    protected Typeface getHelvetica() {
        return helvetica;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = activity;
    }
}
