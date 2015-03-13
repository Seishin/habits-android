package co.naughtyspirit.habits.net.models.reward;

import java.util.ArrayList;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 3/13/15.
 * *
 * * NaughtySpirit 2015
 */
public class RewardsList {
    private ArrayList<Reward> rewards = new ArrayList<>();

    public ArrayList<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(ArrayList<Reward> rewards) {
        this.rewards = rewards;
    }

    @Override
    public String toString() {
        return "RewardsList{" +
                "rewards=" + rewards +
                '}';
    }
}
