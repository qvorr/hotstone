package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;

public class MusliBarEffect implements CardEffectStrategy {
    @Override
    public String getEffectDescription() {
        return "Add +1 mana to hero.";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {
        game.decreaseHeroMana(who, -1);
    }
}
