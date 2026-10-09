package hotstone.standard.Hero;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.framework.Strategy.HeroTypeSelectionStrategy;
import hotstone.standard.GameConstants;

import java.util.HashMap;
import java.util.Map;

public class SemiHeroStrategy implements HeroStrategy {
    private final Map<Player, String> heroTypeOfPlayer = new HashMap<>();
    private final Map<String, HeroStrategy> powerOfHeroType = new HashMap<>();

    public SemiHeroStrategy(HeroTypeSelectionStrategy heroTypeSelection,
                            HeroStrategy gammaHeroes, HeroStrategy epsilonHeroes) {
        powerOfHeroType.put(GameConstants.THAI_CHEF_HERO_TYPE, gammaHeroes);
        powerOfHeroType.put(GameConstants.DANISH_CHEF_HERO_TYPE, gammaHeroes);
        powerOfHeroType.put(GameConstants.FRENCH_CHEF_HERO_TYPE, epsilonHeroes);
        powerOfHeroType.put(GameConstants.ITALIAN_CHEF_HERO_TYPE, epsilonHeroes);

        heroTypeOfPlayer.put(Player.FINDUS, heroTypeSelection.selectHeroType(Player.FINDUS));
        heroTypeOfPlayer.put(Player.PEDDERSEN, heroTypeSelection.selectHeroType(Player.PEDDERSEN));
    }

    @Override
    public String getHeroType(Player who) {
        return heroTypeOfPlayer.get(who);
    }

    @Override
    public void useHeroPower(MutableGame game, Player who) {
        HeroStrategy heroPower = powerOfHeroType.get(getHeroType(who));
        heroPower.useHeroPower(game, who);
    }

    @Override
    public String getHeroPowerEffectDescription(String heroType) {
        HeroStrategy heroPower = powerOfHeroType.get(heroType);
        return heroPower.getHeroPowerEffectDescription(heroType);
    }
}