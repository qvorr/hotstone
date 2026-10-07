package hotstone.framework.Strategy;

import hotstone.framework.MutableCard;
import hotstone.standard.StdCard;
import java.util.List;

public interface DeckStrategy {
    String[] getDeck();
    List<MutableCard> arrangeDeck(List<MutableCard> deck);
}
