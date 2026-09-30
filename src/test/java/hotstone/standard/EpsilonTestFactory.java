package hotstone.standard;

import hotstone.framework.Strategy.HeroStrategy;
import hotstone.standard.Factories.EpsilonFactory;
import hotstone.standard.Hero.EpsilonHeroStrategy;

public class EpsilonTestFactory extends EpsilonFactory {
    int index;
    public EpsilonTestFactory(int index) {this.index = index;}

    @Override
    public HeroStrategy createHeroStrategy()    {return new EpsilonHeroStrategy(new FixedMinionSelectionStub(index));}// unique
}
