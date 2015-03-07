package co.naughtyspirit.habits.net.models;

import android.text.TextUtils;

import com.google.gson.annotations.SerializedName;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/1/15.
 * *
 * * NaughtySpirit 2015
 */
public class Habit {
    @SerializedName("_id")
    private String id;
    private String text;
    private String state;

    public Habit(String text) {
        this.text = text;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getState() {
        if (TextUtils.isEmpty(state)) {
            return 0;
        } else {
            return Integer.valueOf(state);
        }
    }

    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", state=" + state +
                '}';
    }
}
