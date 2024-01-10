package model.quests;

public class Reward {
    private int coin = 0;
    private boolean unlock = false;
    private boolean weapon;

    public int getCoin() {
        return coin;
    }

    public boolean getUnlock() {
        return unlock;
    }

    public boolean getWeapon() {
        return weapon;
    }
    @Override
    public String toString(){
        StringBuilder reward = new StringBuilder("");
        if (coin != 0)
            reward.append(coin);
        if (unlock != false)
            reward.append(true);
        if (weapon)
            reward.append(true);
        return reward.toString();
        }

}
