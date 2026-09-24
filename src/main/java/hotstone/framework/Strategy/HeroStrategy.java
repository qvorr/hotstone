package hotstone.framework.Strategy;

import hotstone.framework.Hero;
import hotstone.framework.Player;
import hotstone.standard.StdCard;

import java.util.List;

public interface HeroStrategy {
        String getHeroType(Player who);

        void useHeroPower(Hero plHero, Hero opHero, List<StdCard> playerField);

        String getHeroPowerEffectDescription(String plHero);
}
