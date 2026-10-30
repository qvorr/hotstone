package hotstone.standard.CardEffects;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.standard.WizardHubAdapter;
import wizardhub.EffectWizard;

public class NoodleSoupEffect implements CardEffectStrategy {
    @Override
    public String getEffectDescription() {
        return "Draw a card.";
    }

    @Override
    public void executeEffect(MutableGame game, Player who) {
        new EffectWizard(new WizardHubAdapter(game, who))
                .forMe()
                .doDrawCard();
    }
}
