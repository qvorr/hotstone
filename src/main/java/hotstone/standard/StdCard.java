package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.framework.Strategy.DeckStrategy;

import java.util.HashMap;
import java.util.Map;

public class StdCard implements Card, MutableCard {
    private final Map<String, int[]> CardAttributes;

    private final String cardName;
    private final Player owner;
    private boolean isActive = false;
    private final CardEffectStrategy es;
    private final DeckStrategy ds;

    public StdCard(String name, Player owner, DeckStrategy ds, CardEffectStrategy es) {
        cardName = name;
        this.owner = owner;
        this.es = es;
        this.ds = ds;
//
////      used as a library of all the card types and default attributes
////      [mana, attack, health]
//        CardAttributes.put(GameConstants.SOVS_CARD, new int[]{0, 1, 1});
//        CardAttributes.put(GameConstants.UNO_CARD, new int[]{1, 1, 1});
//        CardAttributes.put(GameConstants.DOS_CARD, new int[]{2, 2, 2});
//        CardAttributes.put(GameConstants.TRES_CARD, new int[]{3, 3, 3});
//        CardAttributes.put(GameConstants.CUATRO_CARD, new int[]{2, 3, 1});
//        CardAttributes.put(GameConstants.CINCO_CARD, new int[]{3, 5, 1});
//        CardAttributes.put(GameConstants.SEIS_CARD, new int[]{2, 1, 3});
//        CardAttributes.put(GameConstants.SIETE_CARD, new int[]{3, 2, 4});
//        CardAttributes.put(GameConstants.BROWN_RICE_CARD, new int[]{1, 1, 2});
//        CardAttributes.put(GameConstants.FRENCH_FRIES_CARD, new int[]{1, 2, 1});
//        CardAttributes.put(GameConstants.GREEN_SALAD_CARD, new int[]{2, 2, 3});
//        CardAttributes.put(GameConstants.TOMATO_SALAD_CARD, new int[]{2, 3, 2});
//        CardAttributes.put(GameConstants.POKE_BOWL_CARD, new int[]{3, 2, 4});
//        CardAttributes.put(GameConstants.PUMPKIN_SOUP_CARD, new int[]{4, 2, 7});
//        CardAttributes.put(GameConstants.NOODLE_SOUP_CARD, new int[]{4, 5, 3});
//        CardAttributes.put(GameConstants.SPRING_ROLLS_CARD, new int[]{5, 3, 7});
//        CardAttributes.put(GameConstants.BAKED_SALMON_CARD, new int[]{5, 8, 2});
        CardAttributes = ds.getCardAttributeList();
    }

    @Override
    public void decreaseHealthBy(int change) {
        CardAttributes.get(cardName)[2] = CardAttributes.get(cardName)[2] - change;
    }

    @Override
    public void increaseAttack(int amount) {
        CardAttributes.get(cardName)[1] = CardAttributes.get(cardName)[1] + amount;
    }

    @Override
    public void setActive(boolean status) {
        isActive = status;
    }

    @Override
    public void triggerEffect(MutableGame game) {
        es.executeEffect(game, getOwner());
    }

    @Override
    public String getName() {
        return cardName;
    }

    @Override
    public int getManaCost() {
        return CardAttributes.get(cardName)[0];
    } // FAKE
    // im not removing these

    @Override
    public int getAttack() {
        return CardAttributes.get(cardName)[1];
    }   // FAKE

    @Override
    public int getHealth() {
        return CardAttributes.get(cardName)[2];
    }   // FAKE

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getEffectDescription() {
        return es.getEffectDescription();
    }
}
