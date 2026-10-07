package hotstone.framework;

import java.util.List;

public interface MutableGame {

    Iterable<? extends Card> getField(Player who);

    int getFieldSize(Player who);

    MutableHero getMutableHero(Player who);

    MutableCard getMutableCardInField(Player who, int index);

    void removeCardOnField(Player player, MutableCard target);

    void addCardToField(Player who, MutableCard card);

}
