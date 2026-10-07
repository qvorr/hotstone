package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;

public class BakedSalmonEffect implements CardEffectStrategy {
    @Override
    public String getEffectDescription() {
        return "Add +2 attack to random opponent minion";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {

    }
}
