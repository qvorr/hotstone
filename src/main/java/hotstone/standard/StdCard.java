package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.Player;
import java.util.HashMap;

public class StdCard implements Card {
    private final HashMap<String, int[]> CardAttributes = new HashMap<>();

    private String cardName;
    private Player owner;

    public StdCard(String name, Player owner){
        cardName = name;
        this.owner = owner;
        CardAttributes.put(GameConstants.UNO_CARD, new int[]{0, 1, 1});
        CardAttributes.put(GameConstants.DOS_CARD, new int[]{1, 1, 1});
        CardAttributes.put(GameConstants.TRES_CARD, new int[]{1, 2, 1});
        CardAttributes.put(GameConstants.CUATRO_CARD, new int[]{2, 2, 2});
        CardAttributes.put(GameConstants.CINCO_CARD, new int[]{2, 1, 3});
        CardAttributes.put(GameConstants.SEIS_CARD, new int[]{3, 3, 3});
        CardAttributes.put(GameConstants.SIETE_CARD, new int[]{4, 3, 4});
    }

    @Override
    public String getName() {
        return cardName;
    }

    public void setName(String name){
        cardName = name;
    }

    @Override
    public int getManaCost() {
        return 2;
    } // FAKE

    @Override
    public int getAttack() {
        return 2;
    }   // FAKE

    @Override
    public int getHealth() {
        return 2;
    }   // FAKE
    @Override
    public boolean isActive() {
        return false;
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
