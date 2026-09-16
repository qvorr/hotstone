package hotstone.framework.Strategy;

import hotstone.standard.StdCard;
import java.util.List;

public interface DeckStrategy {
    String[] getDeck();
    List<StdCard> arrangeDeck(List<StdCard> deck);
}
