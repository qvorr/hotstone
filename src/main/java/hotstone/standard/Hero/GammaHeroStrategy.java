package hotstone.standard.Hero;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StdCard;
import hotstone.standard.StdHero;

import java.util.List;

public class GammaHeroStrategy implements HeroStrategy {
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
