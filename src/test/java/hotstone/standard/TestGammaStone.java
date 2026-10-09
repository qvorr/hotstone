package hotstone.standard;


import hotstone.framework.Game;

import hotstone.framework.*;
import hotstone.standard.Deck.AlphaDeckStrategy;
import hotstone.standard.Factories.GammaFactory;
import hotstone.standard.Hero.GammaHeroStrategy;
import hotstone.standard.Mana.AlphaManaStrategy;
import hotstone.standard.Winner.GammaWinnerStrategy;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class TestGammaStone {
    private Game game;
    @BeforeEach
    public void SetUp(){
        game = new StandardHotStoneGame(new GammaFactory());
    }

    @Test
    public void findusShouldBeThaiChef(){
        assertThat(game.getHero(Player.FINDUS).getType(), is(GameConstants.THAI_CHEF_HERO_TYPE));
    }

    @Test
    public void peddersenShouldBeDanishChef(){
        assertThat(game.getHero(Player.PEDDERSEN).getType(), is(GameConstants.DANISH_CHEF_HERO_TYPE));
    }

    @Test
    public void findusHeroPowerShouldDamageOpponent(){
        int originalOpHealth = game.getHero(Player.computeOpponent(Player.FINDUS)).getHealth();
        game.usePower(game.getPlayerInTurn());
        int newOpHealth = game.getHero(Player.computeOpponent(Player.FINDUS)).getHealth();
        assertThat(originalOpHealth - newOpHealth, is(2));
    }

    @Test
    public void peddersenHeroPowerShouldSpawnSovs(){
        game.endTurn();
        game.usePower(Player.PEDDERSEN);
        assertThat(game.getField(Player.PEDDERSEN).iterator().next().getName(), is(GameConstants.SOVS_CARD));
    }

    @Test
    public void afterTurn3IfBothFieldsEmptyFindusWins(){
        TestHelper.advanceGameNRounds(game, 4);
        assertThat(game.getWinner(), is(Player.PEDDERSEN));
    }

    @Test
    public void afterTurn3IfBothFieldsAreNotEmptyNobodyWins(){
        game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
        game.endTurn();
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 0), 0);
        game.endTurn();
        TestHelper.advanceGameNRounds(game, 3);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void afterTurn3IfFindusHasEmptyFieldPeddersenWins(){
        game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
        game.endTurn();
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 0), 0);
        game.endTurn();
        TestHelper.advanceGameNRounds(game, 3);
        game.endTurn();
        game.attackCard(Player.PEDDERSEN, game.getCardInField(Player.PEDDERSEN, 0), game.getCardInField(Player.FINDUS, 0));
        assertThat(game.getWinner(), is(Player.PEDDERSEN));
    }

    @Test
    public void afterTurn3IfPeddersenHasEmptyFieldFindusWins(){
        game.endTurn();
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
        game.endTurn();
        game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 1), 0);
        TestHelper.advanceGameNRounds(game, 3);
        game.attackCard(Player.FINDUS, game.getCardInField(Player.FINDUS, 0), game.getCardInField(Player.PEDDERSEN, 0));
        assertThat(game.getWinner(), is(Player.FINDUS));
    }

    @Test
    public void afterTurn3IfPeddersenHasEmptyFieldAndIsPeddersenTurnFindusWins(){
        game.endTurn();
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
        game.endTurn();
        game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 1), 0);
        TestHelper.advanceGameNRounds(game, 3);
        game.attackCard(Player.FINDUS, game.getCardInField(Player.FINDUS, 0), game.getCardInField(Player.PEDDERSEN, 0));
        game.endTurn();
        assertThat(game.getWinner(), is(Player.FINDUS));
    }

    @Test
    public void beforeTurn3IfFindusHasEmptyFieldNobodyWinsYet(){
        game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
        game.endTurn();
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 0), 0);
        game.endTurn();
        game.endTurn();
        game.attackCard(Player.PEDDERSEN, game.getCardInField(Player.PEDDERSEN, 0), game.getCardInField(Player.FINDUS, 0));
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void afterTurn3IfPeddersenHasEmptyFieldNobodyWinsYet(){
        game.endTurn();
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
        game.endTurn();
        game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 0), 0);
        game.endTurn();
        TestHelper.advanceGameNRounds(game, 3);
        game.attackCard(Player.FINDUS, game.getCardInField(Player.FINDUS, 0), game.getCardInField(Player.PEDDERSEN, 0));
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void afterTurn3IfBothFieldsEmptyNobodyWindsYet(){
        TestHelper.advanceGameNRounds(game, 3);
        assertThat(game.getWinner(), is(nullValue()));
    }

    @Test
    public void effectDescForThaiChefShouldReturnItsProperDesc(){
        assertThat(game.getHero(Player.FINDUS).getEffectDescription(), is("Deal 2 damage to opponent hero."));
    }

    @Test
    public void effectDescForDanishChefShouldReturnItsProperDesc(){
        assertThat(game.getHero(Player.PEDDERSEN).getEffectDescription(), is("Summon Sovs card."));
    }
}
