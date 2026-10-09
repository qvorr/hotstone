package hotstone.standard.Factories;

import hotstone.framework.AbstractFactory;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.Deck.ThetaDeckStrategy;
import hotstone.standard.Hero.*;
import hotstone.standard.Mana.BetaManaStrategy;
import hotstone.standard.Winner.BetaWinnerStrategy;

public class SemiStoneFactory implements AbstractFactory {
    public DeckStrategy createDeckStrategy()    {return new ThetaDeckStrategy(); }
    public HeroStrategy createHeroStrategy() {
        return new SemiHeroStrategy(new RandomHeroTypeSelectionStrategy(),
                new GammaHeroStrategy(),
                new EpsilonHeroStrategy(new RandomMinionSelectionStrategy()));
    }
    public ManaStrategy createManaStrategy()    {return new BetaManaStrategy(); }
    public WinnerStrategy createWinnerStrategy(){return new BetaWinnerStrategy();}
}