package hotstone.standard.Deck;

import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardCategoryStrategy;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.standard.CardEffects.*;
import hotstone.standard.Categories.OrdinaryCategory;
import hotstone.standard.Categories.SpellCategory;
import hotstone.standard.Categories.TauntCategory;
import hotstone.standard.GameConstants;
import hotstone.standard.Hero.RandomMinionSelectionStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ThetaDeckStrategy extends EtaDeckStrategy {

    @Override
    public String[] getDeck(){
        String[] etaDeck = super.getDeck();
        String[] thetaCards = new String[]{
                GameConstants.SHRIMP_COCKTAIL_CARD,
                GameConstants.TZATZIKI_CARD,
                GameConstants.LASAGNA_CARD,
                GameConstants.SHRIMP_COCKTAIL_CARD,
                GameConstants.TZATZIKI_CARD,
                GameConstants.LASAGNA_CARD,
        };
        String[] thetaDeck = new String[thetaCards.length + etaDeck.length];
        for(int i = 0; i < thetaDeck.length; i++){
            if(i < etaDeck.length){
                thetaDeck[i] = etaDeck[i];
            } else {
                thetaDeck[i] = thetaCards[i-etaDeck.length];
            }
        }

        return thetaDeck;
    }

    @Override
    public Map<String, int[]> getCardAttributeList() {
        Map<String, int[]> CardAttributes = super.getCardAttributeList();
        CardAttributes.put(GameConstants.SHRIMP_COCKTAIL_CARD, new int[]{2, 2, 2});
        CardAttributes.put(GameConstants.TZATZIKI_CARD, new int[]{3, 3, 2});
        CardAttributes.put(GameConstants.LASAGNA_CARD, new int[]{5, 2, 5});
        CardAttributes.put(GameConstants.MUSLI_BAR_CARD, new int[]{0, 0, 0});
        return CardAttributes;
    }

    @Override
    public CardEffectStrategy getEffect(String name) {
        if (name.equals(GameConstants.MUSLI_BAR_CARD)) {
            return new MusliBarEffect();
        } else {
            return super.getEffect(name);
        }
    }

    @Override
    public CardCategoryStrategy getCategory(String name){
        return switch (name){
            case GameConstants.SHRIMP_COCKTAIL_CARD,
                 GameConstants.TZATZIKI_CARD,
                 GameConstants.LASAGNA_CARD -> new TauntCategory();
            case GameConstants.MUSLI_BAR_CARD -> new SpellCategory();
            default -> new OrdinaryCategory();
        };
    }

    @Override
    public void dealExtraCards(MutableGame game){
        game.addCardToHand(Player.PEDDERSEN,
                game.createMutableCard(Player.PEDDERSEN,
                        GameConstants.MUSLI_BAR_CARD,
                        this, getEffect(GameConstants.MUSLI_BAR_CARD)));
    }
}
