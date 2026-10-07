package hotstone.framework.Strategy;

import hotstone.framework.Hero;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.standard.StdCard;

import java.util.List;

public interface HeroStrategy {

    String getHeroType(Player who);

    void useHeroPower(MutableGame game, Player who);

    String getHeroPowerEffectDescription(String plHero);
}
