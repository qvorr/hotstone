package hotstone.standard.Mana;

import hotstone.framework.Strategy.ManaStrategy;

public class DeltaManaStrategy implements ManaStrategy {
    @Override
    public int manaCalculator(int playerNumber){
        return 5;
    }

    @Override
    public int initialMana() {
        return 5;
    }

}
