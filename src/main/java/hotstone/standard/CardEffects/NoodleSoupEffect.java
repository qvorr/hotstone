package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;

public class NoodleSoupEffect implements CardEffectStrategy {
    @Override
    public String getEffectDescription() {
        return "Draw a card.";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {
        game.drawCard(who);
    }
}
