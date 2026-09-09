package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.Player;
import java.util.HashMap;
import java.util.Map;

public class StdCard implements Card {
    private final Map<String, int[]> CardAttributes = new HashMap<>();

    private String cardName;
    private Player owner;
    private boolean isActive = false;

    public StdCard(String name, Player owner){
        cardName = name;
        this.owner = owner;
//      [mana, attack, health]
        CardAttributes.put(GameConstants.UNO_CARD, new int[]{1, 1, 1});
        CardAttributes.put(GameConstants.DOS_CARD, new int[]{2, 2, 2});
        CardAttributes.put(GameConstants.TRES_CARD, new int[]{3, 3, 3});
        CardAttributes.put(GameConstants.CUATRO_CARD, new int[]{2, 3, 1});
        CardAttributes.put(GameConstants.CINCO_CARD, new int[]{3, 5, 1});
        CardAttributes.put(GameConstants.SEIS_CARD, new int[]{2, 1, 3});
        CardAttributes.put(GameConstants.SIETE_CARD, new int[]{3, 2, 4});
    }

    public void setHealth(int newHealth){
       CardAttributes.get(cardName)[2] = newHealth;
    }

    public void setActive(boolean status){
        isActive = status;
    }

    @Override
    public String getName() {
        return cardName;
    }

    @Override
    public int getManaCost() {
        return CardAttributes.get(cardName)[0];
    } // FAKE

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
        return "";
    }
}
