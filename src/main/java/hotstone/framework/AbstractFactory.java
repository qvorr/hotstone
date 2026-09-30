package hotstone.framework;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Strategy.WinnerStrategy;


// This is the Abstract Factory, which will be used to define what each variant(alpha, beta, gamma ...) will actually implement

public interface AbstractFactory {
    DeckStrategy    createDeckStrategy();
    HeroStrategy    createHeroStrategy();
    ManaStrategy    createManaStrategy();
    WinnerStrategy  createWinnerStrategy();
}