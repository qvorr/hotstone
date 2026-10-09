package hotstone.standard.Deck;

import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.standard.CardEffects.NoEffect;
import hotstone.standard.GameConstants;
import hotstone.standard.StdCard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AlphaDeckStrategy implements DeckStrategy {
    @Override
    public String[] getDeck() {
        String[] AlphaStoneDeck = {
                GameConstants.UNO_CARD,
                GameConstants.DOS_CARD,
                GameConstants.TRES_CARD,
                GameConstants.CUATRO_CARD,
                GameConstants.CINCO_CARD,
                GameConstants.SEIS_CARD,
                GameConstants.SIETE_CARD,
//                 GameConstants.SOVS_CARD
        };
        return AlphaStoneDeck;
    }

    @Override
    public Map<String, int[]> getCardAttributeList() {
        Map<String, int[]> CardAttributes = new HashMap<>();
        CardAttributes.put(GameConstants.SOVS_CARD, new int[]{0, 1, 1});
        CardAttributes.put(GameConstants.UNO_CARD, new int[]{1, 1, 1});
        CardAttributes.put(GameConstants.DOS_CARD, new int[]{2, 2, 2});
        CardAttributes.put(GameConstants.TRES_CARD, new int[]{3, 3, 3});
        CardAttributes.put(GameConstants.CUATRO_CARD, new int[]{2, 3, 1});
        CardAttributes.put(GameConstants.CINCO_CARD, new int[]{3, 5, 1});
        CardAttributes.put(GameConstants.SEIS_CARD, new int[]{2, 1, 3});
        CardAttributes.put(GameConstants.SIETE_CARD, new int[]{3, 2, 4});
        return CardAttributes;
    }

    @Override
    public List<MutableCard> arrangeDeck(List<MutableCard> deck) {
        return deck; // natural build order, unchanged
    }

    @Override
    public CardEffectStrategy effectPicker(String name) {
        return new NoEffect();
    }

}
