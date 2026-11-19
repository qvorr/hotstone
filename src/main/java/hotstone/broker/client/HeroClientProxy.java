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
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.HERO_GET_HEALTH, Integer.class);
    }

    @Override
    public boolean canUsePower() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.HERO_CAN_USE_POWER, Boolean.class);
    }

    @Override
    public String getType() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.HERO_GET_TYPE, String.class);
    }

    @Override
    public Player getOwner() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.HERO_GET_OWNER, Player.class);
    }

    @Override
    public String getEffectDescription() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.HERO_GET_EFFECT_DESCRIPTION, String.class);
    }
}
