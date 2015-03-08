package co.naughtyspirit.habits.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import co.naughtyspirit.habits.utils.FontsLoaderUtil;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/22/15.
 * *
 * * NaughtySpirit 2015
 */
public class CustomFontTextView extends TextView {

    public CustomFontTextView(Context context) {
        this(context, null);
    }

    public CustomFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        
        setTypeface(FontsLoaderUtil.getHelveticaNeueLight(context));
    }
}
