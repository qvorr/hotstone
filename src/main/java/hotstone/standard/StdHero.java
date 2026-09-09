package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.Player;

public class StdHero implements Hero {
    private String heroType = GameConstants.BABY_HERO_TYPE;
    private int health = GameConstants.HERO_MAX_HEALTH;
    private int mana = 3;



    @Override
    public int getMana() {
        return mana; // as per the book, section 37.2 mentioned,
//      "at the start of each player's turn, the mana crystals available to the hero is set to exactly 3,
//      irrespective of how many mana was left from the previous turn"
    }

    public void setMana(int num){
        mana = num;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean canUsePower() {
        return false;
    }

    @Override
    public String getType() {
        return heroType;
    }

    @Override
    public Player getOwner() {
        return null;
    }

    @Override
    public String getEffectDescription() {
        return "";
    }

}
