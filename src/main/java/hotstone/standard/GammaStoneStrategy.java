package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.StrategyMaster;

import java.util.List;
import java.util.Map;

public class GammaStoneStrategy implements StrategyMaster {
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
        Player attacker = player.getOwner();
        Player defender = opponent.getOwner();
        if(turnNumber>3){
            if(field.get(attacker).isEmpty() && field.get(defender).isEmpty()){
                return Player.FINDUS;
            } else if(field.get(defender).isEmpty()){
                return player.getOwner();
            }
        }
        return null;
    }

    @Override
    public String getHeroType(Player who) {
        if(who == Player.FINDUS){
            return GameConstants.THAI_CHEF_HERO_TYPE;
        } else return GameConstants.DANISH_CHEF_HERO_TYPE;
    }

    @Override
    public void useHeroPower(Hero playerHero, Hero opponentHero, List<StdCard> playerField) {
       StdHero player =  (StdHero) playerHero;
       StdHero opponent =  (StdHero) opponentHero;
       if(player.getType().equals(GameConstants.THAI_CHEF_HERO_TYPE)){
           opponent.setHealth(2);
       }else if(player.getType().equals(GameConstants.DANISH_CHEF_HERO_TYPE)){
           playerField.addLast(new StdCard(GameConstants.SOVS_CARD, playerHero.getOwner()));
       }
    }

    @Override
    public String getHeroPowerEffectDescription(Hero plHero) {
        return switch (plHero.getType()) {
            case GameConstants.BABY_HERO_TYPE -> "Just Cute.";
            case GameConstants.THAI_CHEF_HERO_TYPE -> "Deal 2 damage to opponent hero.";
            case GameConstants.DANISH_CHEF_HERO_TYPE -> "Summon Sovs card.";
            default -> "";
        };
    }
}
