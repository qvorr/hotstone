package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Strategy.WinnerStrategy;

import java.util.List;
import java.util.Map;

public class AlphaStoneStrategy implements ManaStrategy, WinnerStrategy {
    @Override
    public int manaCalculator(int playerNumber) {
        return 3;
    }

    @Override
    public int initialMana() {
        return 3;
    }

    @Override
    public Player getWinner(Hero player, Hero opponent, Map<Player, List<StdCard>> field, int turnNumber) {
        if(turnNumber >= 8){
            return Player.FINDUS;
        }else{
            return null;
        }
    }
}
