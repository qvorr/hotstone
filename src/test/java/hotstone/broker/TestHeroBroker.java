package hotstone.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.HeroClientProxy;
import hotstone.broker.doubles.LocalMethodClientRequestHandler;
import hotstone.broker.doubles.StubGameForBroker;
import hotstone.broker.server.HotStoneGameInvoker;
import hotstone.framework.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class TestHeroBroker {
    private Game gameClientProxy;
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
    public void getFindusHeroMana(){
        int mana = hero.getMana();
        assertThat(mana, is(2));
    }



}
