package hotstone.standard;

import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import wizardhub.HotStoneGameTarget;

public class WizardHubAdapter implements HotStoneGameTarget {
    private final MutableGame game;
    private final Player playerInTurn;

    public WizardHubAdapter(MutableGame game, Player playerInTurn) {
        this.game = game;
        this.playerInTurn = playerInTurn;
    }

    @Override
    public int getThePlayerInTurn() {
        return toIndex(playerInTurn);
    }

    @Override
    public int getSizeOfField(int playerIndex) {
        return game.getFieldSize(toPlayer(playerIndex));
    }

    @Override
    public void deltaHealthHero(int playerIndex, int healthDelta) {
        // decreaseHeroHealth subtracts, so the sign is flipped
        game.decreaseHeroHealth(toPlayer(playerIndex), -healthDelta);
    }

    @Override
    public void deltaAttackMinion(int playerIndex, int fieldIndex, int attackDelta) {
        Player who = toPlayer(playerIndex);
        game.increaseAttackofCardInField(who, fieldIndex, attackDelta);
        // attack may not go below 0
        int attack = game.getMutableCardInField(who, fieldIndex).getAttack();
        if (attack < 0) {
            game.increaseAttackofCardInField(who, fieldIndex, -attack);
        }
    }

    @Override
    public void deltaHealthOrRemoveMinion(int playerIndex, int fieldIndex, int healthDelta) {
        Player who = toPlayer(playerIndex);
        MutableCard minion = game.getMutableCardInField(who, fieldIndex);
        minion.decreaseHealthBy(-healthDelta);
        if (minion.getHealth() <= 0) {
            game.removeCardOnField(who, minion);
        }
    }

    @Override
    public void drawCard(int playerIndex) {
        game.drawCard(toPlayer(playerIndex));
    }

    @Override
    public void setTauntCategoryMinion(int playerIndex, int fieldIndex, boolean isTaunt) {
        // not used, the deck decides the category of a card
    }

    private Player toPlayer(int playerIndex) {
        return playerIndex == 0 ? Player.FINDUS : Player.PEDDERSEN;
    }

    private int toIndex(Player player) {
        return player == Player.FINDUS ? 0 : 1;
    }
}