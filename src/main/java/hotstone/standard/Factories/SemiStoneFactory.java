package hotstone.standard.Factories;

import hotstone.framework.AbstractFactory;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Strategy.WinnerStrategy;
import hotstone.standard.Deck.EtaDeckStrategy;
import hotstone.standard.Deck.ThetaDeckStrategy;
import hotstone.standard.Hero.AlphaHeroStrategy;
import hotstone.standard.Mana.BetaManaStrategy;
import hotstone.standard.Mana.DeltaManaStrategy;
import hotstone.standard.Winner.AlphaWinnerStrategy;
import hotstone.standard.Winner.BetaWinnerStrategy;

public class SemiStoneFactory implements AbstractFactory {
    public DeckStrategy createDeckStrategy()    {return new ThetaDeckStrategy(); }// unique
    public HeroStrategy createHeroStrategy()    {return new AlphaHeroStrategy(); }
    public ManaStrategy createManaStrategy()    {return new BetaManaStrategy(); }// unique
    public WinnerStrategy createWinnerStrategy(){return new BetaWinnerStrategy();}
}
