package hotstone.standard;

import hotstone.standard.Factories.ZetaFactory;
import org.junit.jupiter.api.BeforeEach;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;
import hotstone.framework.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestZetaStone {
    private Game game;

    /** Fixture for AlphaStone testing. */
    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new ZetaFactory());
    }

    @Test
    public void byTurn3IfBothFieldsEmptyFindusWins(){
        TestHelper.advanceGameNRounds(game, 4);
        assertThat(game.getWinner(), is(Player.PEDDERSEN));
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
    public void peddersenShouldWinWhenFindusHealthIs0AfterRound6(){
        TestHelper.advanceGameNRounds(game, 6);
        StdHero hero = (StdHero) game.getHero(Player.FINDUS);
        hero.decreaseHealthBy(21);
        assertThat(game.getWinner(), is(Player.PEDDERSEN));
    }
}
