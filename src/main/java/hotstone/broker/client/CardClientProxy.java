package hotstone.broker.client;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotstone.broker.common.OperationNames;
import hotstone.framework.Card;
import hotstone.framework.Player;

import javax.management.OperationsException;

public class CardClientProxy implements Card, ClientProxy {

    private final Requestor requestor;
    private final String objid = "pendingg";

    public CardClientProxy(Requestor requestor){
        this.requestor = requestor;
    }



    @Override
    public String getName() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.CARD_GET_NAME, String.class);
    }

    @Override
    public int getManaCost() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.CARD_GET_MANA_COST, Integer.class);
    }

    @Override
    public int getAttack() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.CARD_GET_ATTACK, Integer.class);
    }

    @Override
    public int getHealth() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.CARD_GET_HEALTH, Integer.class);
    }

    @Override
    public boolean isActive() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.CARD_IS_ACTIVE, Boolean.class);
    }

    @Override
    public Player getOwner() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.CARD_GET_OWNER, Player.class);
    }

    @Override
    public String getEffectDescription() {
        return requestor.sendRequestAndAwaitReply(objid, OperationNames.CARD_GET_EFFECT_DESCRIPTION, String.class);
    }
}
