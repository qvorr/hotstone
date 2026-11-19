package hotstone.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.HeroClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.doubles.StubGameForBroker;
import hotstone.broker.server.HotStoneGameInvoker;
import hotstone.framework.Game;
import hotstone.framework.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class TestHeroBroker {
    private HeroClientProxy hero;
    private StandardJSONRequestor requestor;

    @BeforeEach
    public void setup(){
        Game servant = new StubGameForBroker();
        Invoker invoker = new HotStoneGameInvoker(servant);
        ClientRequestHandler crh =
                new LocalMethodClientRequestHandler(invoker);
        requestor = new StandardJSONRequestor(crh);
        hero = new HeroClientProxy(requestor);
    }

    @Test
    public void getHeroMana(){
        int mana = hero.getMana();
        assertThat(mana, is(1));
    }

    @Test
    public void getHeroHealth(){
        int health = hero.getHealth();
        assertThat(health, is(7));
    }

    @Test
    public void canHeroUsePower(){
        boolean can = hero.canUsePower();
        assertThat(can, is(false));
    }

    @Test
    public void getHeroOwner(){
        Player owner = hero.getOwner();
        assertThat(owner, is(Player.FINDUS));
    }

}
