package hotstone.standard;

import hotstone.framework.Game;
import hotstone.framework.MutableCard;
import hotstone.framework.MutableGame;
import hotstone.framework.Player;
import hotstone.framework.Strategy.CardEffectStrategy;
import hotstone.standard.CardEffects.*;
import hotstone.standard.Deck.AlphaDeckStrategy;
import hotstone.standard.Deck.DeltaDeckStrategy;
import hotstone.standard.Deck.EtaDeckStrategy;
import hotstone.standard.Factories.EtaFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.MatcherAssert.assertThat;

public class TestEtaStone {
    private SpyMutableGame spy;

    @BeforeEach
    public void SetUp(){
        spy= new SpyMutableGame(new EtaFactory());
    }

    @Test
    public void brownRiceShouldDeal1DamageToOpHero(){
        CardEffectStrategy brownRice = new BrownRiceEffect();
        brownRice.executeEffect(spy, Player.FINDUS);
        assertThat(spy.lastCall, is("decreaseHeroHealth"));
    }

    @Test
    public void pokeBowlShouldHeal2WhenExecuted(){
        CardEffectStrategy pokeBowl = new PokeBowlEffect();
        pokeBowl.executeEffect(spy, Player.FINDUS);
        assertThat(spy.lastCall, is("decreaseHeroHealth"));
    }

    @Test
    public void noodleSoupShouldDrawACardToThePlayer(){
        CardEffectStrategy noodleSoup = new NoodleSoupEffect();
        int sizeBefore = spy.getHandSize(Player.FINDUS);
        assertThat(sizeBefore, is(3));
        noodleSoup.executeEffect(spy, Player.FINDUS);
        int sizeAfter = spy.getHandSize(Player.FINDUS);
        assertThat(sizeAfter, is(4));
        assertThat(spy.lastCall,  is("drawCard"));
    }

    @Test
    public void bakedSalmonShouldAdd2AttackToRandomOpponentMinion(){
        CardEffectStrategy bakedSalmon = new BakedSalmonEffect(new FixedMinionSelectionStub(0));
        spy.addCardToField(Player.FINDUS, new StdCard(GameConstants.PUMPKIN_SOUP_CARD, Player.FINDUS, new EtaDeckStrategy(), new NoEffect()));
        bakedSalmon.executeEffect(spy, Player.PEDDERSEN);
        assertThat(spy.lastCall, is("increaseAttackOfCardInField"));
    }

    @Test
    public void springRollsShouldDestroyARandomOpponentMinion(){
        CardEffectStrategy springRoll = new SpringRollsEffect(new FixedMinionSelectionStub(0));
        spy.addCardToField(Player.FINDUS, new StdCard(GameConstants.PUMPKIN_SOUP_CARD, Player.FINDUS, new EtaDeckStrategy(), new NoEffect()));
        springRoll.executeEffect(spy, Player.PEDDERSEN);
        assertThat(spy.lastCall, is("removeCardOnField"));
    }

    @Test
    public void tomatoSaladShouldAdd1AttackToRandomMinion(){
        CardEffectStrategy tomatoSalad = new TomatoSaladEffect(new FixedMinionSelectionStub(0));
        spy.addCardToField(Player.FINDUS, new StdCard(GameConstants.PUMPKIN_SOUP_CARD, Player.FINDUS, new EtaDeckStrategy(), new NoEffect()));
        tomatoSalad.executeEffect(spy, Player.PEDDERSEN);
        assertThat(spy.lastCall, is("increaseAttackOfCardInField"));
    }

}
