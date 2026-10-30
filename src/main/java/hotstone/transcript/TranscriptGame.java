package hotstone.transcript;

import hotstone.framework.*;
import hotstone.observer.GameObserver;


public class TranscriptGame implements Game {
    private final Game game;

    public TranscriptGame(Game game) {
        this.game = game;
    }

   
    @Override
    public void endTurn() {
        Player who = game.getPlayerInTurn();
        game.endTurn();
        System.out.println(who + " ended turn.");
    }

    @Override
    public Status playCard(Player who, Card card, int atIndex) {
        Status status = game.playCard(who, card, atIndex);
        if (status == Status.OK) {
            System.out.println(who + " played " + nameOf(card) + " at index " + atIndex + ".");
        }
        return status;
    }

    @Override
    public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        Status status = game.attackCard(playerAttacking, attackingCard, defendingCard);
        if (status == Status.OK) {
            System.out.println(playerAttacking + " attacked " + nameOf(defendingCard)
                    + " with " + nameOf(attackingCard) + ".");
        }
        return status;
    }

    @Override
    public Status attackHero(Player playerAttacking, Card attackingCard) {
        Status status = game.attackHero(playerAttacking, attackingCard);
        if (status == Status.OK) {
            System.out.println(playerAttacking + " attacked Hero with " + nameOf(attackingCard) + ".");
        }
        return status;
    }

    @Override
    public Status usePower(Player who) {
        Status status = game.usePower(who);
        if (status == Status.OK) {
            System.out.println(who + " used Hero power.");
        }
        return status;
    }

   
    private String nameOf(Card card) {
        return card.getName().replace('-', ' ');
    }


    @Override
    public void addObserver(GameObserver observer) { game.addObserver(observer); }

  

    @Override
    public Player getPlayerInTurn() { return game.getPlayerInTurn(); }

    @Override
    public Hero getHero(Player who) { return game.getHero(who); }

    @Override
    public Player getWinner() { return game.getWinner(); }

    @Override
    public int getTurnNumber() { return game.getTurnNumber(); }

    @Override
    public int getDeckSize(Player who) { return game.getDeckSize(who); }

    @Override
    public Card getCardInHand(Player who, int indexInHand) { return game.getCardInHand(who, indexInHand); }

    @Override
    public Iterable<? extends Card> getHand(Player who) { return game.getHand(who); }

    @Override
    public int getHandSize(Player who) { return game.getHandSize(who); }

    @Override
    public Card getCardInField(Player who, int indexInField) { return game.getCardInField(who, indexInField); }

    @Override
    public Iterable<? extends Card> getField(Player who) { return game.getField(who); }

    @Override
    public int getFieldSize(Player who) { return game.getFieldSize(who); }
}
