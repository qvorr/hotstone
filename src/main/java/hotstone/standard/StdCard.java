package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.Player;

public class StdCard implements Card {
    private String cardName;

    @Override
    public String getName() {
        return cardName;
    }
    public StdCard(String name){
        cardName = name;
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
        return null;
    }

    @Override
    public String getEffectDescription() {
        return "";
    }
}
