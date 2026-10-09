package hotstone.framework.Strategy;

import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;

import java.util.List;
import java.util.Map;

public interface DeckStrategy {
    String[] getDeck();

    Map<String, int[]> getCardAttributeList();

    List<MutableCard> arrangeDeck(List<MutableCard> deck);

    CardEffectStrategy getEffect(String name);

    CardCategoryStrategy getCategory(String name);

    void dealExtraCards(MutableGame game);
}
