package hotstone.standard;

import hotstone.framework.*;
import hotstone.standard.CardEffects.NoEffect;
import hotstone.standard.Deck.ThetaDeckStrategy;
import hotstone.standard.Factories.ThetaFactory;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class TestThetaStone {
    MutableGame game;
    @BeforeEach
    public void SetUp(){
        game = new StandardHotStoneGame(new ThetaFactory());
    }

    @Test
    public void shouldBeAbleToAttackTauntCard(){
        game.addCardToField(Player.FINDUS, new StdCard(GameConstants.TZATZIKI_CARD, Player.FINDUS, new ThetaDeckStrategy(), new NoEffect()));
        MutableCard defendingCard = game.getMutableCardInField(Player.FINDUS, 0);
        MutableCard attackingCard = game.getMutableCardInHand(Player.PEDDERSEN, 0);
        game.addCardToField(Player.FINDUS, defendingCard);
        game.addCardToField(Player.PEDDERSEN, attackingCard);
        attackingCard.setActive(true);
        defendingCard.setActive(true);
        StandardHotStoneGame gameStd = (StandardHotStoneGame) game;
        System.out.println(defendingCard.isTaunt(defendingCard.getName()));
        System.out.println(attackingCard.isTaunt(attackingCard.getName()));
        gameStd.endTurn();
        assertThat(gameStd.isAttackOnCardPossible(Player.PEDDERSEN,
                gameStd.getCardInField(Player.PEDDERSEN, 0),
                gameStd.getCardInField(Player.FINDUS, 0)), is(Status.OK));
    }

    @Test
    public void shouldNotBeAbleToAttackNonTauntCardWhenTauntIsOnOpponentField(){
        game.addCardToField(Player.FINDUS, new StdCard(GameConstants.TZATZIKI_CARD, Player.FINDUS, new ThetaDeckStrategy(), new NoEffect()));
        game.addCardToField(Player.FINDUS, game.getMutableCardInHand(Player.FINDUS, 0));

        MutableCard defendingCard = game.getMutableCardInField(Player.FINDUS, 1);
        MutableCard attackingCard = game.getMutableCardInHand(Player.PEDDERSEN, 0);

        game.addCardToField(Player.PEDDERSEN, attackingCard);
        attackingCard.setActive(true);
        defendingCard.setActive(true);
        StandardHotStoneGame gameStd = (StandardHotStoneGame) game;

        gameStd.endTurn();
        assertThat(gameStd.isAttackOnCardPossible(Player.PEDDERSEN,
                gameStd.getCardInField(Player.PEDDERSEN, 0),
                gameStd.getCardInField(Player.FINDUS, 1)), is(Status.ATTACK_NOT_ALLOWED_ON_NON_TAUNT_MINION));
    }

    @Test
    public void atStartPeddersenShouldHaveMuslibarCard(){
        assertThat(game.getHandSize(Player.PEDDERSEN), is(4));
        assertThat(game.getMutableCardInHand(Player.PEDDERSEN, 3).getName(), is(GameConstants.MUSLI_BAR_CARD));
    }
}
