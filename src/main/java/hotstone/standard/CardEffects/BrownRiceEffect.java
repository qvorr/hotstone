package hotstone.standard.CardEffects;

import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.MutableHero;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;

public class BrownRiceEffect implements CardEffectStrategy {
    @Override
    public String getEffectDescription() {
        return  "Deal 1 damage to opponent hero.";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {
        MutableHero opHero = game.getMutableHero(Player.computeOpponent(who));
        opHero.decreaseHealthBy(1);
    }
}
