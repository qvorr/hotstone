package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.framework.Strategy.MinionSelectionStrategy;

public class SpringRollsEffect implements CardEffectStrategy {
    private MinionSelectionStrategy mss;
    public SpringRollsEffect (MinionSelectionStrategy mss){
        this.mss = mss;
    }

    @Override
    public String getEffectDescription() {
        return "Destroy a random opponent minion";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {
        Player op = Player.computeOpponent(who);
        boolean isFieldEmpty = game.getFieldSize(op) == 0;
        if(isFieldEmpty){
            return;
        }
        int index = mss.selectMinionIndex(game.getFieldSize(op));
        game.removeCardOnField(op, game.getMutableCardInField(op, index));
    }
}
