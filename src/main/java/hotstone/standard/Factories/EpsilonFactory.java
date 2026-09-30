package hotstone.standard.Factories;

import hotstone.framework.AbstractFactory;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.Deck.AlphaDeckStrategy;
import hotstone.standard.Hero.EpsilonHeroStrategy;
import hotstone.standard.Hero.RandomMinionSelectionStrategy;
import hotstone.standard.Mana.AlphaManaStrategy;
import hotstone.standard.Winner.AlphaWinnerStrategy;

public class EpsilonFactory implements AbstractFactory {
    public DeckStrategy createDeckStrategy()    {return new AlphaDeckStrategy(); }
    public HeroStrategy createHeroStrategy()    {return new EpsilonHeroStrategy(new RandomMinionSelectionStrategy());}// unique
    public ManaStrategy createManaStrategy()    {return new AlphaManaStrategy(); }
    public WinnerStrategy createWinnerStrategy(){return new AlphaWinnerStrategy();}
}

