package hotstone.framework.Strategy;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.standard.StdCard;

import java.util.List;
import java.util.Map;

public interface WinnerStrategy {
    Player getWinner(Hero player, Hero opponent, Map<Player, List<StdCard>> field, int turnNumber);
}
