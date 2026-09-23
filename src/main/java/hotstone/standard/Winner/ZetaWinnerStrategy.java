package hotstone.standard.Winner;


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
    public Player getWinner(Hero player, Hero opponent, Map<Player, List<StdCard>> field, int turnNumber) {
        if( turnNumber < 12 ){
            return gws.getWinner(player, opponent, field, turnNumber);
        } else {
            return bws.getWinner(player, opponent, field, turnNumber);
        }
    }
}
