package hotstone.standard.Deck;

import hotstone.framework.Strategy.DeckStrategy;
import hotstone.standard.GameConstants;
import hotstone.standard.StdCard;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class DeltaDeckStrategy implements DeckStrategy {
    @Override
    public String[] getDeck() {
        String[] DeltaStoneDeck = {
                GameConstants.BROWN_RICE_CARD,
                GameConstants.FRENCH_FRIES_CARD,
                GameConstants.GREEN_SALAD_CARD,
                GameConstants.TOMATO_SALAD_CARD,
                GameConstants.POKE_BOWL_CARD,
                GameConstants.PUMPKIN_SOUP_CARD,
                GameConstants.NOODLE_SOUP_CARD,
                GameConstants.SPRING_ROLLS_CARD,
                GameConstants.BAKED_SALMON_CARD,
                GameConstants.BROWN_RICE_CARD,
                GameConstants.FRENCH_FRIES_CARD,
                GameConstants.GREEN_SALAD_CARD,
                GameConstants.TOMATO_SALAD_CARD,
                GameConstants.POKE_BOWL_CARD,
                GameConstants.PUMPKIN_SOUP_CARD,
                GameConstants.NOODLE_SOUP_CARD,
                GameConstants.SPRING_ROLLS_CARD,
                GameConstants.BAKED_SALMON_CARD,
//              GameConstants.SOVS_CARD
        };
        return DeltaStoneDeck;
    }

    @Override
    public List<StdCard> arrangeDeck(List<StdCard> deck) {
        List<StdCard> arranged = new ArrayList<>(deck);   // copy, don't mutate caller's list
        Random random = new Random();
        Collections.shuffle(arranged, random);

        moveSuitableCardToFront(arranged, 0, 4);  // becomes the 3rd card
        moveSuitableCardToFront(arranged, 1, 2);  // becomes the 2nd card
        moveSuitableCardToFront(arranged, 2, 1);  // becomes the 1st card

        return arranged;
    }

    private void moveSuitableCardToFront(List<StdCard> list, int searchStartIndex, int maxManaCost) {
        for (int i = searchStartIndex; i < list.size(); i++) {
            if (list.get(i).getManaCost() <= maxManaCost) {
                StdCard found = list.remove(i);
                list.addFirst(found);
                return;
            }
        }
    }
}
