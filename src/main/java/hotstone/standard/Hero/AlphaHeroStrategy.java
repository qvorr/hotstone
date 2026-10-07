package hotstone.standard.Hero;

import hotstone.framework.Hero;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StdCard;

import java.util.List;

public class AlphaHeroStrategy implements HeroStrategy {
    @Override
    public String getHeroType(Player who) {
        return GameConstants.BABY_HERO_TYPE;
    }

    @Override
    public void useHeroPower(MutableGame game, Player who) {
        // does nothing
    }

    @Override
    public String getHeroPowerEffectDescription(String plHero) {
        return "Just Cute.";
    }
}
