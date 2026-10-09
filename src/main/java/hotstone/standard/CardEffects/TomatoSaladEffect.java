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
        Player opponent = Player.computeOpponent(who);
        int totalSize = 0;
        int playerFieldSize = game.getFieldSize(who);
        int opponentFieldSize = game.getFieldSize(Player.computeOpponent(who));
        totalSize = playerFieldSize + opponentFieldSize;

        if(totalSize == 0) {
            return;
        }

        int index = mss.selectMinionIndex(totalSize);
        if (index < playerFieldSize){
            game.increaseAttackofCardInField(who, index, 1);
        } else {
            index = index - playerFieldSize;
            game.increaseAttackofCardInField(opponent, index, 1);
        }

    }
}
