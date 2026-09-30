package hotstone.standard.Factories;

import hotstone.framework.AbstractFactory;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.Deck.AlphaDeckStrategy;
import hotstone.standard.Hero.AlphaHeroStrategy;
import hotstone.standard.Mana.BetaManaStrategy;
import hotstone.standard.Winner.BetaWinnerStrategy;

public class BetaFactory implements AbstractFactory {
    public DeckStrategy createDeckStrategy()    {return new AlphaDeckStrategy(); }
    public HeroStrategy createHeroStrategy()    {return new AlphaHeroStrategy(); }
    public ManaStrategy createManaStrategy()    {return new BetaManaStrategy(); }// unique
    public WinnerStrategy createWinnerStrategy(){return new BetaWinnerStrategy();}// unique
}
