package hotstone.standard;

import hotstone.framework.Game;

import hotstone.framework.*;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestBetaStone {
    private Game game;
    @BeforeEach
    public void SetUp(){
        game = new StandardHotStoneGame(new BetaStoneStrategy());
    }

    @Test
    public void findusShouldStartWith1Mana(){
        assertThat(game.getHero(Player.FINDUS).getMana(), is(1));
    }

    @Test
    public void playersManaShouldIncreaseEachRound(){
        for(int i = 1; i <= 4; i++){
            assertThat(game.getHero(Player.FINDUS).getMana(), is(i));
            game.endTurn();
            assertThat(game.getHero(Player.PEDDERSEN).getMana(), is(i));
            game.endTurn();
        }
    }
    @Test
    public void playerManaShouldCapAt7(){
        TestHelper.advanceGameNRounds(game, 8);
        assertThat(game.getHero(Player.FINDUS).getMana(), is(7));
    }
}
