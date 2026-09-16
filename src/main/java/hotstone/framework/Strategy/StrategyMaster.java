package hotstone.framework.Strategy;

public interface StrategyMaster extends ManaStrategy, HeroStrategy, WinnerStrategy{
    // used to combine all smaller interfaces to
    // help simplify the StandardHotStoneGame constructor
    // it doesn't have any methods of its own
}
