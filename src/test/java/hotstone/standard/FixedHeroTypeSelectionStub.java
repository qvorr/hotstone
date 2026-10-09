package hotstone.standard;

import hotstone.framework.Player;
import hotstone.framework.Strategy.HeroTypeSelectionStrategy;

public class FixedHeroTypeSelectionStub implements HeroTypeSelectionStrategy {
    private final String findusHeroType;
    private final String peddersenHeroType;

    public FixedHeroTypeSelectionStub(String findusHeroType, String peddersenHeroType) {
        this.findusHeroType = findusHeroType;
        this.peddersenHeroType = peddersenHeroType;
    }

    @Override
    public String selectHeroType(Player who) {
        if (who == Player.FINDUS) {
            return findusHeroType;
        }
        return peddersenHeroType;
    }
}