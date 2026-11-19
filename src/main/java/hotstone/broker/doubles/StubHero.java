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
