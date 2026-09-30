package hotstone.standard.Hero;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StdCard;

import java.util.List;

public class EpsilonHeroStrategy implements HeroStrategy {


    @Override
    public String getHeroType(Player who) {
        return GameConstants.FRENCH_CHEF_HERO_TYPE;
    }

    @Override
    public void useHeroPower(Hero plHero, Hero opHero, List<StdCard> playerField) {

    }

    @Override
    public String getHeroPowerEffectDescription(String plHero) {
        return "";
    }
}
