package hotstone.standard.Deck;

import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.standard.CardEffects.*;
import hotstone.standard.GameConstants;
import hotstone.standard.Hero.RandomMinionSelectionStrategy;

import java.util.*;

public class EtaDeckStrategy implements DeckStrategy {
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
    public Map<String, int[]> getCardAttributeList() {
        Map<String, int[]> CardAttributes = new HashMap<>();
        CardAttributes.put(GameConstants.SOVS_CARD, new int[]{0, 1, 1});
        CardAttributes.put(GameConstants.BROWN_RICE_CARD, new int[]{1, 1, 1});
        CardAttributes.put(GameConstants.FRENCH_FRIES_CARD, new int[]{1, 2, 1});
        CardAttributes.put(GameConstants.GREEN_SALAD_CARD, new int[]{2, 2, 3});
        CardAttributes.put(GameConstants.TOMATO_SALAD_CARD, new int[]{2, 2, 2});
        CardAttributes.put(GameConstants.POKE_BOWL_CARD, new int[]{3, 2, 3});
        CardAttributes.put(GameConstants.PUMPKIN_SOUP_CARD, new int[]{4, 2, 7});
        CardAttributes.put(GameConstants.NOODLE_SOUP_CARD, new int[]{4, 5, 3});
        CardAttributes.put(GameConstants.SPRING_ROLLS_CARD, new int[]{5, 3, 5});
        CardAttributes.put(GameConstants.BAKED_SALMON_CARD, new int[]{5, 7, 6});
        return CardAttributes;
    }

    @Override
    public List<MutableCard> arrangeDeck(List<MutableCard> deck) {
        List<MutableCard> arranged = new ArrayList<>(deck);   // copy, don't mutate caller's list
        Random random = new Random();
        Collections.shuffle(arranged, random);

        moveSuitableCardToFront(arranged, 0, 4);  // becomes the 3rd card
        moveSuitableCardToFront(arranged, 1, 2);  // becomes the 2nd card
        moveSuitableCardToFront(arranged, 2, 1);  // becomes the 1st card

        return arranged;
    }

    @Override
    public CardEffectStrategy effectPicker(String name) {
        return switch (name) {
            case GameConstants.BROWN_RICE_CARD -> new BrownRiceEffect();
            case GameConstants.TOMATO_SALAD_CARD -> new TomatoSaladEffect(new RandomMinionSelectionStrategy());
            case GameConstants.POKE_BOWL_CARD -> new PokeBowlEffect();
            case GameConstants.NOODLE_SOUP_CARD -> new NoodleSoupEffect();
            case GameConstants.SPRING_ROLLS_CARD -> new SpringRollsEffect(new RandomMinionSelectionStrategy());
            case GameConstants.BAKED_SALMON_CARD -> new BakedSalmonEffect(new RandomMinionSelectionStrategy());
            default -> new NoEffect();

        };
    }

    private void moveSuitableCardToFront(List<MutableCard> list, int searchStartIndex, int maxManaCost) {
        for (int i = searchStartIndex; i < list.size(); i++) {
            if (list.get(i).getManaCost() <= maxManaCost) {
                MutableCard found = list.remove(i);
                list.addFirst(found);
                break;
            }
        }
    }
}
