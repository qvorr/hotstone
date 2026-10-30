package hotstone.standard;

import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.standard.Factories.AlphaFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestObserverNotification {
    Game game;
    SpyGameObserver spy;
    @BeforeEach
    public void setUp(){
        game = new StandardHotStoneGame(new AlphaFactory());
        spy = new SpyGameObserver();
        game.addObserver(spy);
    }

    @Test
    public void shouldNotifyWhenPlayCardSucceeds(){
        Card card = game.getCardInHand(Player.FINDUS, 0);
        game.playCard(Player.FINDUS, card, 0);
        assertThat(spy.eventLog, hasItem("onPlayCard(FINDUS, Tres, 0)"));
        assertThat(spy.eventLog, hasItem("onHeroUpdate(FINDUS)"));
    }

    @Test
    public void shouldNotNotifyWhenActionFails(){
        Card card = game.getCardInHand(Player.FINDUS, 0);
        Status status = game.playCard(Player.PEDDERSEN, card, 0);
        assertThat(status, is(Status.NOT_PLAYER_IN_TURN));
        assertThat(spy.eventLog.size(), is(0));
    }

    @Test
    public void changingTurnsShouldEmitDirectAndIndirectEvents(){
        game.endTurn();
        assertThat(spy.eventLog, hasItem("onChangeTurnTo(PEDDERSEN)"));
        assertThat(spy.eventLog, hasItem("onHeroUpdate(PEDDERSEN)"));
    }
}
