package co.naughtyspirit.habits.net.models.reward;

import com.google.gson.annotations.SerializedName;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/13/15.
 * *
 * * NaughtySpirit 2015
 */
public class Reward {
    @SerializedName("_id")
    private String id;
    @SerializedName("user")
    private String userId;
    private String text;
    private int gold;

    public Reward(String text, int gold) {
        this.text = text;
        this.gold = gold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getGold() {
        return gold;
    }

    public String getGoldAsString() {
        return String.valueOf(gold);
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    @Override
    public String toString() {
        return "Reward{" +
                "id='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", text='" + text + '\'' +
                ", gold=" + gold +
                '}';
    }
}
