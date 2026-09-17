package hotstone.standard.Mana;

import hotstone.framework.Strategy.ManaStrategy;

public class AlphaManaStrategy implements ManaStrategy {
    @Override
    public int manaCalculator(int playerNumber) {
        return 3;
    }

    @Override
    public int initialMana() {
        return 3;
    }

}
