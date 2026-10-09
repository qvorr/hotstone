package hotstone.standard;

import hotstone.framework.*;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestSemiStone {
    private Game game;

    @BeforeEach
    public void setUp() {
        game = createGameWithHeroes(GameConstants.THAI_CHEF_HERO_TYPE, GameConstants.ITALIAN_CHEF_HERO_TYPE);
    }

    private Game createGameWithHeroes(String findusHeroType, String peddersenHeroType) {
        return new StandardHotStoneGame(new SemiStoneTestFactory(findusHeroType, peddersenHeroType));
    }

    @Test
    public void playersShouldGetTheSelectedHeroTypes() {
        assertThat(game.getHero(Player.FINDUS).getType(), is(GameConstants.THAI_CHEF_HERO_TYPE));
        assertThat(game.getHero(Player.PEDDERSEN).getType(), is(GameConstants.ITALIAN_CHEF_HERO_TYPE));
    }

    @Test
    public void heroPowerDescriptionShouldFollowTheHeroType() {
        assertThat(game.getHero(Player.FINDUS).getEffectDescription(), is("Deal 2 damage to opponent hero."));
        assertThat(game.getHero(Player.PEDDERSEN).getEffectDescription(), is("Add 2 attack to minion."));
    }

    @Test
    public void thaiChefPowerShouldDeal2DamageToOpponentHero() {
        TestHelper.advanceGameNRounds(game, 1); // Findus now has 2 mana
        game.usePower(Player.FINDUS);
        assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(19));
    }

    @Test
    public void danishChefPowerShouldSummonSovsOnOwnField() {
        game = createGameWithHeroes(GameConstants.DANISH_CHEF_HERO_TYPE, GameConstants.FRENCH_CHEF_HERO_TYPE);
        TestHelper.advanceGameNRounds(game, 1);
        game.usePower(Player.FINDUS);
        assertThat(game.getFieldSize(Player.FINDUS), is(1));
        assertThat(game.getCardInField(Player.FINDUS, 0).getName(), is(GameConstants.SOVS_CARD));
    }

    @Test
    public void frenchChefPowerShouldDamageOpponentMinion() {
        game = createGameWithHeroes(GameConstants.FRENCH_CHEF_HERO_TYPE, GameConstants.DANISH_CHEF_HERO_TYPE);
        TestHelper.advanceGameNRounds(game, 1);
        game.endTurn();
        game.usePower(Player.PEDDERSEN); // Peddersen summons Sovs (health 1)
        game.endTurn();
        game.usePower(Player.FINDUS);    // Redwine kills the Sovs
        assertThat(game.getFieldSize(Player.PEDDERSEN), is(0));
    }
}