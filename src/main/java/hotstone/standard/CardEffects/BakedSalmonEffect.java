package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.framework.Strategy.MinionSelectionStrategy;
import hotstone.standard.WizardHubAdapter;
import wizardhub.EffectWizard;

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
        int fieldSize = game.getFieldSize(op);
        if (fieldSize == 0) {
            return;
        }
        int index = mss.selectMinionIndex(fieldSize);
        new EffectWizard(new WizardHubAdapter(game, who))
                .forOpponent().forMinionAt(index)
                .doChangeAttack(+2);
    }
}
