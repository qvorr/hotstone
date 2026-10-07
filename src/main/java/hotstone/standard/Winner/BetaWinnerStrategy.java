package hotstone.standard.Winner;

import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.StdCard;

import java.util.List;
import java.util.Map;

public class BetaWinnerStrategy implements WinnerStrategy {

    @Override
    public Player getWinner(Game game) {
        Hero player = game.getHero(game.getPlayerInTurn());
        Hero opponent = game.getHero(Player.computeOpponent(game.getPlayerInTurn()));
        if(player.getHealth() <= 0){
            return opponent.getOwner();
        }else if(opponent.getHealth() <= 0){
            return player.getOwner();
        }else{
            return null;
        }
    }
}
