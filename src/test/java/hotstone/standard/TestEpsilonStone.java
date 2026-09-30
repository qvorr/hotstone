package hotstone.standard;

import hotstone.framework.*;
import hotstone.standard.Deck.AlphaDeckStrategy;
import hotstone.standard.Factories.EpsilonFactory;
import hotstone.standard.Hero.EpsilonHeroStrategy;
import hotstone.standard.Mana.AlphaManaStrategy;
import hotstone.standard.Winner.AlphaWinnerStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class TestEpsilonStone {
    private Game game;

    /**
     * Fixture for EpsilonStone testing.
     */
    @BeforeEach
    public void setUp() {
        game = createGameSelectingMinionAt(0);
    }

    private Game createGameSelectingMinionAt(int index) {
        return new StandardHotStoneGame(new EpsilonTestFactory(index));
    }


    @Test
    public void findusShouldBeFrenchChef() {
        assertThat(game.getHero(Player.FINDUS).getType(), is(GameConstants.FRENCH_CHEF_HERO_TYPE));
    }

    @Test
    public void peddersenShouldBeItalianChef() {
        assertThat(game.getHero(Player.PEDDERSEN).getType(), is(GameConstants.ITALIAN_CHEF_HERO_TYPE));
    }

    @Test
    public void frenchChefShouldHaveRedwineDescription() {
        assertThat(game.getHero(Player.FINDUS).getEffectDescription(),
                is("Deal 2 damage to opponent minion."));
    }

    @Test
    public void italianChefShouldHavePastaDescription() {
        assertThat(game.getHero(Player.PEDDERSEN).getEffectDescription(),
                is("Add 2 attack to minion."));
    }

    @Test
    public void redwineShouldDeal2DamageToOpponentMinion() {
        game.endTurn();
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 0), 0);
        game.endTurn();
        game.usePower(Player.FINDUS);
        assertThat(game.getCardInField(Player.PEDDERSEN, 0).getHealth(), is(1));
    }

    @Test
    public void pastaShouldAdd2AttackToOwnMinion() {
        game.endTurn();
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
        assertThat(game.getCardInField(Player.PEDDERSEN, 0).getAttack(), is(1));
        game.usePower(Player.PEDDERSEN);
        assertThat(game.getCardInField(Player.PEDDERSEN, 0).getAttack(), is(3));
    }

    @Test
    public void redwineShouldHitTheSelectedMinionAndRemoveItIfDead() {
        game = createGameSelectingMinionAt(1);
        game.endTurn();
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 1), 0);
        game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 1), 1);
        game.endTurn();
        assertThat(game.getFieldSize(Player.PEDDERSEN), is(2));
        game.usePower(Player.FINDUS);
        assertThat(game.getFieldSize(Player.PEDDERSEN), is(1));
        assertThat(game.getCardInField(Player.PEDDERSEN, 0).getName(), is(GameConstants.DOS_CARD));
        assertThat(game.getCardInField(Player.PEDDERSEN, 0).getHealth(), is(2));
    }

    @Test
    public void redwineShouldHaveNoEffectWhenOpponentFieldIsEmpty() {
        assertThat(game.usePower(Player.FINDUS), is(Status.OK));
        assertThat(game.getFieldSize(Player.PEDDERSEN), is(0));
    }

    @Test
    public void pastaShouldHaveNoEffectWhenOwnFieldIsEmpty() {
        game.endTurn();
        assertThat(game.usePower(Player.PEDDERSEN), is(Status.OK));
        assertThat(game.getFieldSize(Player.PEDDERSEN), is(0));
    }
}
