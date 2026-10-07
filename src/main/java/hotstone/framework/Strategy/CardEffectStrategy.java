package hotstone.framework.Strategy;

import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;

public interface CardEffectStrategy {
    String getEffectDescription();
    void executeEffect(MutableGame game, Player who);
}
