package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.StrategyMaster;

import java.util.List;
import java.util.Map;

public class BetaStoneStrategy implements StrategyMaster {
    @Override
    public int manaCalculator(int turnNum) {
        if(turnNum < 14){
            return turnNum/2 + initialMana();
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

    @Override
    public String getHeroType(Player who) {
        return GameConstants.BABY_HERO_TYPE;
    }

    @Override
    public void useHeroPower(Hero plHero, Hero opHero, List<StdCard> playerField) {
        // does nothing
    }

    @Override
    public String[] getDeck() {
        String[] AlphaStoneDeck = {
                GameConstants.UNO_CARD,
                GameConstants.DOS_CARD,
                GameConstants.TRES_CARD,
                GameConstants.CUATRO_CARD,
                GameConstants.CINCO_CARD,
                GameConstants.SEIS_CARD,
                GameConstants.SIETE_CARD,
//                 GameConstants.SOVS_CARD
        };
        return AlphaStoneDeck;
    }

    @Override
    public List<StdCard> arrangeDeck(List<StdCard> deck) {
        return deck; // natural build order, unchanged
    }

    @Override
    public String getHeroPowerEffectDescription(Hero plHero) {
        return "Just Cute.";
    }
}
