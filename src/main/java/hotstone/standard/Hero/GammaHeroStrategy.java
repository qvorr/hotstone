package hotstone.standard.Hero;

import hotstone.framework.*;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.standard.CardEffects.NoEffect;
import hotstone.standard.GameConstants;
import hotstone.standard.StdCard;

public class GammaHeroStrategy implements HeroStrategy {
    @Override
    public String getHeroType(Player who) {
        if(who == Player.FINDUS){
            return GameConstants.THAI_CHEF_HERO_TYPE;
        } else return GameConstants.DANISH_CHEF_HERO_TYPE;
    }

    @Override
    public void useHeroPower(MutableGame game, Player who) {
//        Hero playerHero, Hero opponentHero, List<StdCard> playerField, List<StdCard> opponentField
//        StdHero player =  (StdHero) playerHero;
//        StdHero opponent =  (StdHero) opponentHero;
        MutableHero player = game.getMutableHero(who);
        MutableHero opponent = game.getMutableHero(Player.computeOpponent(who));
        Iterable<? extends Card> playerField = game.getField(who);


        if(player.getType().equals(GameConstants.THAI_CHEF_HERO_TYPE)){
            opponent.decreaseHealthBy(2);
        }else if(player.getType().equals(GameConstants.DANISH_CHEF_HERO_TYPE)){
            game.addSovsToField(who);
        }
    }

    @Override
    public String getHeroPowerEffectDescription(String plHero) {
        return switch (plHero) {
            case GameConstants.THAI_CHEF_HERO_TYPE -> "Deal 2 damage to opponent hero.";
            case GameConstants.DANISH_CHEF_HERO_TYPE -> "Summon Sovs card.";
            default -> "";
        };
    }
}
