package hotstone.standard.Factories;

import hotstone.framework.AbstractFactory;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.Deck.AlphaDeckStrategy;
import hotstone.standard.Hero.GammaHeroStrategy;
import hotstone.standard.Mana.AlphaManaStrategy;
import hotstone.standard.Winner.GammaWinnerStrategy;

public class GammaFactory implements AbstractFactory {
    public DeckStrategy createDeckStrategy()    {return new AlphaDeckStrategy(); }
    public HeroStrategy createHeroStrategy()    {return new GammaHeroStrategy(); }// unique
    public ManaStrategy createManaStrategy()    {return new AlphaManaStrategy(); }
    public WinnerStrategy createWinnerStrategy(){return new GammaWinnerStrategy();}// unique
}
