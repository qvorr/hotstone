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
        return 0;
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
