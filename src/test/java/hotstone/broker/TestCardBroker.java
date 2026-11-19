package hotstone.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.CardClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.doubles.StubGameForBroker;
import hotstone.broker.server.HotStoneGameInvoker;
import hotstone.framework.Game;
import hotstone.framework.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class TestCardBroker {
    private CardClientProxy card;
    private StandardJSONRequestor requestor;

    @BeforeEach
    public void setup(){
        Game servant = new StubGameForBroker();
        Invoker invoker = new HotStoneGameInvoker(servant);
        ClientRequestHandler crh =
                new LocalMethodClientRequestHandler(invoker);
        requestor = new StandardJSONRequestor(crh);
        card = new CardClientProxy(requestor);
    }

    @Test
    public void getCardName(){
        String name = card.getName();
        assertThat(name, is("Siete"));
    }

    @Test
    public void getCardManaCost(){
        int mana = card.getManaCost();
        assertThat(mana, is(1));
    }

    @Test
    public void getCardAttack(){
        int atk = card.getAttack();
        assertThat(atk, is(1));
    }

    @Test
    public void getCardHealth(){
        int health = card.getHealth();
        assertThat(health, is(5));
    }

    @Test
    public void isCardActive(){
        boolean isActive = card.isActive();
        assertThat(isActive, is(false));
    }

    @Test
    public void getCardOwner(){
        Player owner = card.getOwner();
        assertThat(owner, is(Player.FINDUS));
    }

    @Test
    public void GetCardEffDesc(){
        String desc = card.getEffectDescription();
        assertThat(desc, is("Six"));
    }
}
