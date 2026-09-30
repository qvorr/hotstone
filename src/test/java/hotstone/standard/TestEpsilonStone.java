package hotstone.standard;
import hotstone.framework.*;
import hotstone.standard.Deck.AlphaDeckStrategy;
import hotstone.standard.Hero.EpsilonHeroStrategy;
import hotstone.standard.Mana.AlphaManaStrategy;
import hotstone.standard.Winner.AlphaWinnerStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestEpsilonStone {
    private Game game;

    /** Fixture for EpsilonStone testing. */
    @BeforeEach
    public void setUp() {
        game = new StandardHotStoneGame(new AlphaManaStrategy(), new AlphaDeckStrategy(), new EpsilonHeroStrategy(), new AlphaWinnerStrategy());

    }


    @Test
    public void findusShouldBeFrenchChef() {
        assertThat(game.getHero(Player.FINDUS).getType(), is(GameConstants.FRENCH_CHEF_HERO_TYPE));
    }
}
