package co.naughtyspirit.habits.net.models.todo;

import com.google.gson.annotations.SerializedName;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/7/15.
 * *
 * * NaughtySpirit 2015
 */
public class ToDo {
    
    @SerializedName("_id")
    private String id;
    private String text;
    private String state;

    public ToDo(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getState() {
        return state;
    }

    public int getStateAsInt() {
        return Integer.valueOf(state);
    }
    
    public boolean getStateAsBoolean() {
        return (getStateAsInt() == 1);
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setState(int state) {
        this.state = String.valueOf(state);
    }

    @Override
    public String toString() {
        return "DailyTask{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
