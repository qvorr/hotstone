package hotstone.standard.Winner;


import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.StdCard;

import java.util.List;
import java.util.Map;

public class ZetaWinnerStrategy implements WinnerStrategy {
    private final WinnerStrategy bws = new BetaWinnerStrategy();
    private final WinnerStrategy gws = new GammaWinnerStrategy();

    @Override
    public Player getWinner(Game game) {
        int turnNumber = game.getTurnNumber();
        if( turnNumber < 12 ){
            return gws.getWinner(game);
        } else {
            return bws.getWinner(game);
        }
    }
}
