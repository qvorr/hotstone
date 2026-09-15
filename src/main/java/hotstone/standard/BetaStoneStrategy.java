package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Game;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Strategy.WinnerStrategy;

import java.util.List;
import java.util.Map;

public class BetaStoneStrategy implements ManaStrategy, WinnerStrategy {
    @Override
    public int manaCalculator(int turnNum) {
        if(turnNum < 14 && turnNum % 2 == 0){
            return turnNum/2 + 1;
        }else return 7;
    }

    @Override
    public int initialMana() {
        return 1;
    }

    @Override
    public Player getWinner(Hero player, Hero opponent, Map<Player, List<StdCard>> field, int playerTurn) {
        if(player.getHealth() <= 0){
            return opponent.getOwner();
        }else if(opponent.getHealth() <= 0){
            return player.getOwner();
        }else{
            return null;
        }
    }
}
