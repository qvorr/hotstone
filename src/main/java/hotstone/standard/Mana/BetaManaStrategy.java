package hotstone.standard.Mana;

import hotstone.framework.Strategy.ManaStrategy;

public class BetaManaStrategy implements ManaStrategy {

    @Override
    public int manaCalculator(int turnNum) {
        if(turnNum < 14){
            return turnNum/2 + initialMana();
        }else return 7;
    }

    @Override
    public int initialMana() {
        return 1;
    }
}
