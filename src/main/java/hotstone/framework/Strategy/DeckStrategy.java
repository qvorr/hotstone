package hotstone.framework.Strategy;

import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.standard.StdCard;
import java.util.List;
import java.util.Map;

public interface DeckStrategy {
    String[] getDeck();

    List<MutableCard> arrangeDeck(List<MutableCard> deck);

    CardEffectStrategy effectPicker(String name);
}
