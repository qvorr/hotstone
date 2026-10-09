package hotstone.standard;


import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.MutableCard;
import hotstone.framework.Player;
import hotstone.framework.Strategy.DeckStrategy;
import hotstone.standard.CardEffects.BakedSalmonEffect;
import hotstone.standard.CardEffects.BrownRiceEffect;
import hotstone.standard.Deck.DeltaDeckStrategy;
import hotstone.standard.Factories.DeltaFactory;
import hotstone.standard.Hero.RandomMinionSelectionStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

public class TestDeltaStone {
    private Game game;
    @BeforeEach
    public void SetUp(){
        game = new StandardHotStoneGame(new DeltaFactory());
    }

    @Test
    public void shouldHave5ManaAtStartOfTurn() {
        // When Findus starts their turn
        int mana = game.getHero(Player.FINDUS).getMana();
        // Then mana available should be exactly 5
        assertThat(mana, is(5));
    }

    @Test
    public void shouldHave15CardsLeftInDeck() {
        //We check how many cards Findus has
        int deckSize = game.getDeckSize(Player.FINDUS);
        //The game starts with 18, he draws 3, should be 15 left
        assertThat(deckSize, is(15));
    }

    @Test
    public void shouldHave2CopiesOfBrownRice() {
        //we count how many Brown Rice are there in a deck
        String[] deckNames = new DeltaDeckStrategy().getDeck();
        int count = 0;
        for (String name : deckNames) {
            if (name.equals(GameConstants.BROWN_RICE_CARD)) {
                count++;
            }
        }
        //Should be 2
        assertThat(count, is(2));
    }

    @Test
    public void firstCardShouldHave1Mana() {
        DeltaDeckStrategy strat = new DeltaDeckStrategy();
        List<MutableCard> raw = new ArrayList<>();
        for (String name : strat.getDeck()) {
            raw.add(new StdCard(name, Player.FINDUS, strat, strat.effectPicker(name)));
        }
        List<MutableCard> arranged = strat.arrangeDeck(raw);

        //this card should cost exactly 1 mana
        assertThat(arranged.get(0).getManaCost(), is(1));
    }

    @Test
    public void secondCardShouldHave2ManaOrLess() {
        DeltaDeckStrategy strat = new DeltaDeckStrategy();
        List<MutableCard> raw = new ArrayList<>();
        for (String name : strat.getDeck()) {
            raw.add(new StdCard(name, Player.FINDUS, strat, strat.effectPicker(name)));
        }
        List<MutableCard> arranged = strat.arrangeDeck(raw);

        //This card should cost 2 mana or less
        assertThat(arranged.get(1).getManaCost(), is(lessThanOrEqualTo(2)));
    }

    @Test
    public void thirdCardShouldHave4ManaOrLess() {
        DeltaDeckStrategy strat = new DeltaDeckStrategy();
        List<MutableCard> raw = new ArrayList<>();
        for (String name : strat.getDeck()) {
            raw.add(new StdCard(name, Player.FINDUS, strat, strat.effectPicker(name)));
        }
        List<MutableCard> arranged = strat.arrangeDeck(raw);

        //This card should cost 4 mana or less
        assertThat(arranged.get(2).getManaCost(), is(lessThanOrEqualTo(4)));
    }

    @Test public void brownRiceShouldHave1Mana1Attack2Health() {
        //we check that Brown_Rice_Card has the correct attributes
        DeltaDeckStrategy strat =  new DeltaDeckStrategy();
        Card brownRice = new StdCard(GameConstants.BROWN_RICE_CARD, Player.FINDUS, strat, new BrownRiceEffect());
        assertThat(brownRice.getManaCost(), is(1));
        assertThat(brownRice.getAttack(), is(1));
        assertThat(brownRice.getHealth(), is(2));
    }

    @Test public void bakedSalmonShouldHave5Mana8Attack2Health() {
        //we check that Brown_Rice_Card has the correct attributes
        DeltaDeckStrategy strat =  new DeltaDeckStrategy();
        Card bakedSalmon = new StdCard(GameConstants.BAKED_SALMON_CARD, Player.FINDUS,strat,  new BakedSalmonEffect(new RandomMinionSelectionStrategy()));
        assertThat(bakedSalmon.getManaCost(), is(5));
        assertThat(bakedSalmon.getAttack(), is(8));
        assertThat(bakedSalmon.getHealth(), is(2));
    }

}
