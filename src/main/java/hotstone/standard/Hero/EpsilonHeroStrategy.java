package hotstone.standard.Hero;

import hotstone.framework.Hero;
import hotstone.framework.Player;
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
    public void useHeroPower(Hero plHero, Hero opHero, List<StdCard> playerField, List<StdCard> opponentField) {
        if (plHero.getType().equals(GameConstants.ITALIAN_CHEF_HERO_TYPE)) {
            if (playerField.isEmpty()) {
                return;
            }
            StdCard target = selectMinion(playerField);
            target.increaseAttack(PASTA_ATTACK_BONUS);
        } else if (plHero.getType().equals(GameConstants.FRENCH_CHEF_HERO_TYPE)) {
            if (opponentField.isEmpty()) {
                return;
            }
            StdCard target = selectMinion(opponentField);
            target.setHealth(REDWINE_DAMAGE);
            if (target.getHealth() <= 0) {
                opponentField.remove(target);
            }
        }
    }

    private StdCard selectMinion(List<StdCard> field) {
        int index = minionSelection.selectMinionIndex(field.size());
        return field.get(index);
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
