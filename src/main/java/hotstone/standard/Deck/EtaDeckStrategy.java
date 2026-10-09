package hotstone.standard.Deck;

import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Strategy.CardCategoryStrategy;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.standard.CardEffects.*;
import hotstone.standard.Categories.OrdinaryCategory;
import hotstone.standard.GameConstants;
import hotstone.standard.Hero.RandomMinionSelectionStrategy;

import java.util.*;

public class EtaDeckStrategy extends DeltaDeckStrategy {

    @Override
    public Map<String, int[]> getCardAttributeList() {
        Map<String, int[]> CardAttributes = super.getCardAttributeList();
        CardAttributes.put(GameConstants.BROWN_RICE_CARD, new int[]{1, 1, 1});
        CardAttributes.put(GameConstants.TOMATO_SALAD_CARD, new int[]{2, 2, 2});
        CardAttributes.put(GameConstants.POKE_BOWL_CARD, new int[]{3, 2, 3});
        CardAttributes.put(GameConstants.NOODLE_SOUP_CARD, new int[]{4, 5, 3});
        CardAttributes.put(GameConstants.SPRING_ROLLS_CARD, new int[]{5, 3, 5});
        CardAttributes.put(GameConstants.BAKED_SALMON_CARD, new int[]{5, 7, 6});
        return CardAttributes;
    }

    @Override
    public CardEffectStrategy getEffect(String name) {
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

    @Override
    public CardCategoryStrategy getCategory(String name) {
        return new OrdinaryCategory();
    }
}
