package hotstone.standard.Hero;

import hotstone.framework.Player;
import hotstone.framework.Strategy.HeroTypeSelectionStrategy;
import hotstone.standard.GameConstants;

import java.util.Random;

public class RandomHeroTypeSelectionStrategy implements HeroTypeSelectionStrategy {
    private static final String[] HERO_TYPES = {
            GameConstants.THAI_CHEF_HERO_TYPE,
            GameConstants.DANISH_CHEF_HERO_TYPE,
            GameConstants.FRENCH_CHEF_HERO_TYPE,
            GameConstants.ITALIAN_CHEF_HERO_TYPE
    };
    private final Random random = new Random();

    @Override
    public String selectHeroType(Player who) {
        return HERO_TYPES[random.nextInt(HERO_TYPES.length)];
    }
}