package hotstone.standard;

import hotstone.framework.Strategy.MinionSelectionStrategy;

public class FixedMinionSelectionStub implements MinionSelectionStrategy {
    private final int indexToSelect;

    public FixedMinionSelectionStub(int indexToSelect) {
        this.indexToSelect = indexToSelect;
    }

    @Override
    public int selectMinionIndex(int numberOfMinions) {
        return indexToSelect;
    }
}