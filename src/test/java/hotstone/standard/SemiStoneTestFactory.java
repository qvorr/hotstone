package hotstone.standard;

import hotstone.framework.Strategy.HeroStrategy;
import hotstone.standard.Factories.SemiStoneFactory;
import hotstone.standard.Hero.EpsilonHeroStrategy;
import hotstone.standard.Hero.GammaHeroStrategy;
import hotstone.standard.Hero.SemiHeroStrategy;

public class SemiStoneTestFactory extends SemiStoneFactory {
    private final String findusHeroType;
    private final String peddersenHeroType;

    public SemiStoneTestFactory(String findusHeroType, String peddersenHeroType) {
        this.findusHeroType = findusHeroType;
        this.peddersenHeroType = peddersenHeroType;
    }

    @Override
    public HeroStrategy createHeroStrategy() {
        return new SemiHeroStrategy(
                new FixedHeroTypeSelectionStub(findusHeroType, peddersenHeroType),
                new GammaHeroStrategy(),
                new EpsilonHeroStrategy(new FixedMinionSelectionStub(0)));
    }
}