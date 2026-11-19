package hotstone.broker.doubles;

import hotstone.framework.Hero;
import hotstone.framework.Player;

public class StubHero implements Hero {
    @Override
    public int getMana() {
        return 2;
    }

    @Override
    public int getHealth() {
        return 20;
    }

    @Override
    public boolean canUsePower() {
        return true;
    }

    @Override
    public String getType() {
        return "Baby";
    }

    @Override
    public Player getOwner() {
        return Player.PEDDERSEN;
    }

    @Override
    public String getEffectDescription() {
        return "Just cute.";
    }
}
