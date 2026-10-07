package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;

public class NoEffect implements CardEffectStrategy {
    @Override
    public String getEffectDescription() {
        return "";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {
        // does nothing <3
    }
}
