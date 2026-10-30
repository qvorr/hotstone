package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.standard.WizardHubAdapter;
import wizardhub.EffectWizard;

public class BrownRiceEffect implements CardEffectStrategy {
    @Override
    public String getEffectDescription() {
        return  "Deal 1 damage to opponent hero.";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {
        new EffectWizard(new WizardHubAdapter(game, who))
                .forOpponent().forHero()
                .doChangeHealthOrRemove(-1);
    }
}
