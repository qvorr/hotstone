package hotstone.standard.Hero;

import hotstone.framework.Strategy.MinionSelectionStrategy;

import java.util.Random;

public class RandomMinionSelectionStrategy implements MinionSelectionStrategy {
    private final Random random = new Random();

    @Override
    public int selectMinionIndex(int numberOfMinions) {
        return random.nextInt(numberOfMinions);
    }
}