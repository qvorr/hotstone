package hotstone.standard.Deck;

import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.standard.CardEffects.NoEffect;
import hotstone.standard.GameConstants;
import hotstone.standard.StdCard;

import java.util.List;

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
    public List<MutableCard> arrangeDeck(List<MutableCard> deck) {
        return deck; // natural build order, unchanged
    }

    @Override
    public CardEffectStrategy effectPicker(String name) {
        return new NoEffect();
    }

}
