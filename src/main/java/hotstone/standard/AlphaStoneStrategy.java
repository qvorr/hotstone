package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.StrategyMaster;

import java.util.List;
import java.util.Map;

public class AlphaStoneStrategy implements StrategyMaster {
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

    @Override
    public String getHeroType(Player who) {
        return GameConstants.BABY_HERO_TYPE;
    }

    @Override
    public void useHeroPower(Hero plHero, Hero opHero, List<StdCard> playerField) {
       // does nothing
    }

    @Override
    public String getHeroPowerEffectDescription(Hero plHero) {
        return "Just Cute.";
    }
}
