package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.Player;

public class StdHero implements Hero {
    private String heroName;
    public StdHero(String name){
        heroName = name;
    }

    @Override
    public int getMana() {
        return 3; // as per the book, section 37.2 mentioned,
//      "at the start of each player's turn, the mana crystals available to the hero is set to exactly 3,
//      irrespective of how many mana was left from the previous turn"
    }


    @Override
    public int getHealth() {
        return 0;
    }

    @Override
    public boolean canUsePower() {
        return false;
    }

    @Override
    public String getType() {
        return "";
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
