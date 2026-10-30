package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.observer.GameObserver;

import java.util.ArrayList;
import java.util.List;

public class SpyGameObserver implements GameObserver {
    public String lastEvent = "none";
    public List<String> eventLog = new ArrayList<>();

    private void record(String eventSignature) {
        this.lastEvent = eventSignature;
        this.eventLog.add(eventSignature);
    }


    @Override
    public void onPlayCard(Player who, Card card, int atIndex) {
        record("onPlayCard(" + who + ", " + card.getName() + ", " + atIndex +")");
    }

    @Override
    public void onChangeTurnTo(Player playerBecomingActive) {
        record("onChangeTurnTo(" + playerBecomingActive + ")");
    }

    @Override
    public void onAttackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
        record("onAttackCard(" + playerAttacking + ", " + attackingCard.getName() + ", " + defendingCard.getName() + ")");
    }

    @Override
    public void onAttackHero(Player playerAttacking, Card attackingCard) {
        record("onAttackHero(" + playerAttacking + ", " + attackingCard.getName() + ")");
    }

    @Override
    public void onUsePower(Player who) {
        record("onUsePower(" + who + ")");
    }

    @Override
    public void onCardDraw(Player who, Card drawnCard) {
        record("onCardDraw(" + who + ", " + drawnCard.getName() + ")");
    }

    @Override
    public void onCardUpdate(Card card) {
        record("onCardUpdate(" + card.getName() + ")");
    }

    @Override
    public void onCardRemove(Player who, Card card) {
        record("onCardRemove(" + who + ", " + card.getName() + ")");
    }

    @Override
    public void onHeroUpdate(Player who) {
        record("onHeroUpdate(" + who + ")");
    }

    @Override
    public void onGameWon(Player playerWinning) {
        record("onGameWon(" + playerWinning + ")");
    }
}
