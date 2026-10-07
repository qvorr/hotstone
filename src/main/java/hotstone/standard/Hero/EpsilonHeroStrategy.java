package hotstone.standard.Hero;

import hotstone.framework.*;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StdCard;
import hotstone.framework.Strategy.MinionSelectionStrategy;

import java.util.List;

public class EpsilonHeroStrategy implements HeroStrategy {
    private static final int PASTA_ATTACK_BONUS = 2;
    private static final int REDWINE_DAMAGE = 2;
    private final MinionSelectionStrategy minionSelection;

    public EpsilonHeroStrategy(MinionSelectionStrategy minionSelection) {
        this.minionSelection = minionSelection;
    }

    @Override
    public String getHeroType(Player who) {
        if (who == Player.FINDUS) {
            return GameConstants.FRENCH_CHEF_HERO_TYPE;
        }
        return GameConstants.ITALIAN_CHEF_HERO_TYPE;
    }

    @Override
    public void useHeroPower(MutableGame game, Player who) {
        MutableHero plHero = game.getMutableHero(who);
        Iterable<? extends Card> playerField = game.getField(who);
        Iterable<? extends Card> opponentField = game.getField(Player.computeOpponent(who));

        boolean heroIsItalianChef = plHero.getType().equals(GameConstants.ITALIAN_CHEF_HERO_TYPE);
        boolean heroIsFrenchChef = plHero.getType().equals(GameConstants.FRENCH_CHEF_HERO_TYPE);
        boolean isPlayerFieldEmpty = !playerField.iterator().hasNext();
        boolean isOpponentFieldEmpty = !opponentField.iterator().hasNext();

        if (heroIsItalianChef) {
            if (isPlayerFieldEmpty) {
                return;
            }
            MutableCard target = selectMinion(game, who);
            target.increaseAttack(PASTA_ATTACK_BONUS);
        } else if (heroIsFrenchChef) {
            if (isOpponentFieldEmpty) {
                return;
            }
            MutableCard target = selectMinion(game, Player.computeOpponent(who));
            target.setHealth(REDWINE_DAMAGE);
            if (target.getHealth() <= 0) {
                game.removeCardOnField(Player.computeOpponent(who), target);
            }
        }
    }

    private MutableCard selectMinion(MutableGame game, Player who) {
        int index = minionSelection.selectMinionIndex(game.getFieldSize(who));
        return game.getMutableCardInField(who, index);
    }

    @Override
    public String getHeroPowerEffectDescription(String plHero) {
        return switch (plHero) {
            case GameConstants.FRENCH_CHEF_HERO_TYPE -> "Deal 2 damage to opponent minion.";
            case GameConstants.ITALIAN_CHEF_HERO_TYPE -> "Add 2 attack to minion.";
            default -> "";
        };
    }

}
