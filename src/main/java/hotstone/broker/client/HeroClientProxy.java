package hotstone.broker.client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotstone.broker.common.OperationNames;
import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;

public class HeroClientProxy implements Hero, ClientProxy {

    private final Requestor requestor;
    private final String objid = "pendingg";

    public HeroClientProxy(Requestor requestor){
        this.requestor = requestor;
    }

    @Override
    public int getMana() {
        // I think with the object id is how we identify which hero we're talking about, without passing
        // arguments to the function. so im leaving it like this and I will pray.
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.HERO_GET_MANA, Integer.class);
    }

    @Override
    public int getHealth() {
        return 0;
    }

    @Override
    public boolean canUsePower() {
        return false;
    }

    @Override
    public String getType() {
        return "";
    }

    @Override
    public Player getOwner() {
        return null;
    }

    @Override
    public String getEffectDescription() {
        return "";
    }
}
