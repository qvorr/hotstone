package hotstone.standard.Winner;

import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.StdCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GammaWinnerStrategy implements WinnerStrategy {
    @Override
    public Player getWinner(Game game) {
        Player attacker = game.getPlayerInTurn();
        Player defender = Player.computeOpponent(attacker);
        int turnNumber = game.getTurnNumber();
        Iterable<? extends Card> attackerField = game.getField(attacker);
        Iterable<? extends Card> defenderField = game.getField(defender);
        boolean isAttackerFieldEmpty = !attackerField.iterator().hasNext();
        boolean isDefenderFieldEmpty = !defenderField.iterator().hasNext();
        if(turnNumber>6){
            if(isAttackerFieldEmpty  && isDefenderFieldEmpty){
                return Player.PEDDERSEN;
            } else if(isDefenderFieldEmpty){
                return attacker;
            } else if(isAttackerFieldEmpty){
                return defender;
            }
        }
        return null;
    }

}
