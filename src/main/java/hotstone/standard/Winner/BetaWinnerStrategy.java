package hotstone.standard.Winner;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.StdCard;

import java.util.List;
import java.util.Map;

public class BetaWinnerStrategy implements WinnerStrategy {

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
