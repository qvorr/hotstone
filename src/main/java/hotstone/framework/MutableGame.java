package hotstone.framework;

import java.util.List;

public interface MutableGame {

    Iterable<? extends Card> getField(Player who);

    int getFieldSize(Player who);

    int getHandSize(Player who);

    Iterable<? extends Card> getHand(Player who);

    MutableCard getMutableCardInHand(Player who, int index);

    MutableHero getMutableHero(Player who);

    MutableCard getMutableCardInField(Player who, int index);

    void removeCardOnField(Player player, MutableCard target);

    void addCardToField(Player who, MutableCard card);

    void drawCard(Player who);

    void addSovsToField();

    void decreaseHeroHealth(Player who, int value);

    void increaseAttackofCardInField(Player who, int index, int value);

}
