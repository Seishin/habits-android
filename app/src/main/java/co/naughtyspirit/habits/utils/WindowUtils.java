package co.naughtyspirit.habits.utils;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/8/15.
 * *
 * * NaughtySpirit 2015
 */
public class WindowUtils {

    public static void hideSoftKeyboard(Context ctx, View v) {
        InputMethodManager imm = (InputMethodManager) ctx.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
}
