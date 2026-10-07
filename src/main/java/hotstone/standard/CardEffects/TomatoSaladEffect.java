package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.framework.Strategy.MinionSelectionStrategy;

public class TomatoSaladEffect implements CardEffectStrategy {
    private MinionSelectionStrategy mss;
    public TomatoSaladEffect(MinionSelectionStrategy mss){
        this.mss = mss;
    }

    @Override
    public String getEffectDescription() {
        return "Add +1 attack to random minion.";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {
        boolean isFieldEmpty = game.getFieldSize(who) == 0;
        if(isFieldEmpty){
            return;
        }
        int index = mss.selectMinionIndex(game.getFieldSize(who));
        game.getMutableCardInField(who, index).increaseAttack(1);
    }
}
