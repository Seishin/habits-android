package co.naughtyspirit.habits.net.models;

/**
 * * Created by Seishin <atanas@naughtyspirit.co>
 * * on 2/27/15.
 * *
 * * NaughtySpirit 2015
 */
public class UserStats {
    
    private int hp;
    private int exp;
    private int nextLvlExp;
    private int gold;
    private int lvl;
    private boolean alive;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public int getGold() {
        return gold;
    }

    public void setGold(int gold) {
        this.gold = gold;
    }

    public int getLvl() {
        return lvl;
    }

    public void setLvl(int lvl) {
        this.lvl = lvl;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getNextLvlExp() {
        return nextLvlExp;
    }

    public void setNextLvlExp(int nextLvlExp) {
        this.nextLvlExp = nextLvlExp;
    }

    @Override
    public String toString() {
        return "UserStats{" +
                "hp=" + hp +
                ", exp=" + exp +
                ", nextLvlExp=" + nextLvlExp +
                ", gold=" + gold +
                ", lvl=" + lvl +
                ", alive=" + alive +
                '}';
    }
}
