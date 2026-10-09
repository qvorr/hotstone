package hotstone.framework.Strategy;

import hotstone.framework.Player;

public interface HeroTypeSelectionStrategy {
    String selectHeroType(Player who);
}