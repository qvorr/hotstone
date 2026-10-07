package hotstone.standard.Winner;

import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.StdCard;

import java.util.List;
import java.util.Map;

public class AlphaWinnerStrategy implements WinnerStrategy {
    @Override
    public Player getWinner(Game game) {
        int turnNumber = game.getTurnNumber();
        if(turnNumber >= 8){
            return Player.FINDUS;
        }else{
            return null;
        }
    }

}
