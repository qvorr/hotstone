package hotstone.standard.Winner;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.StdCard;

import java.util.List;
import java.util.Map;

public class GammaWinnerStrategy implements WinnerStrategy {
    @Override
    public Player getWinner(Hero player, Hero opponent, Map<Player, List<StdCard>> field, int turnNumber) {
        Player attacker = player.getOwner();
        Player defender = opponent.getOwner();
        if(turnNumber>6){
            if(field.get(attacker).isEmpty() && field.get(defender).isEmpty()){
                return Player.PEDDERSEN;
            } else if(field.get(defender).isEmpty()){
                return player.getOwner();
            }
        }
        return null;
    }

}
