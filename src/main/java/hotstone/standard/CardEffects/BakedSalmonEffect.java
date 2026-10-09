package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.framework.Strategy.MinionSelectionStrategy;

public class BakedSalmonEffect implements CardEffectStrategy {
    private MinionSelectionStrategy mss;
    public BakedSalmonEffect(MinionSelectionStrategy mss){
        this.mss = mss;
    }
    @Override
    public String getEffectDescription() {
        return "Add +2 attack to random opponent minion";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {
        Player op = Player.computeOpponent(who);
        boolean isFieldEmpty = game.getFieldSize(op) == 0;
        if(isFieldEmpty){
            return;
        }
        int index = mss.selectMinionIndex(game.getFieldSize(op));
        game.increaseAttackofCardInField(op, index, 2);
    }
}
