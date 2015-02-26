package co.naughtyspirit.habits.utils;

import android.content.Context;
import android.graphics.Typeface;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/22/15.
 * *
 * * NaughtySpirit 2015
 */
public class FontsLoaderUtil {

    public static Typeface getHelveticaNeue(Context ctx) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/HelveticaNeue.otf");

        return font;
    }

    public static Typeface getHelveticaNeueLight(Context ctx) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/HelveticaNeue-Light.otf");

        return font;
    }

    public static Typeface getHelveticaNeueMedium(Context ctx) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/HelveticaNeue-Medium.otf");

        return font;
    }
}