package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;

public class PokeBowlEffect implements CardEffectStrategy {
    @Override
    public String getEffectDescription() {
        return "Restore +2 health to hero.";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {
        game.getMutableHero(who).decreaseHealthBy(-2);
    }
}
