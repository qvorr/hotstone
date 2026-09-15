package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Player;

public class StdHero implements Hero {
    private String heroType = GameConstants.BABY_HERO_TYPE;
    private int health = GameConstants.HERO_MAX_HEALTH;
    private String effectDesc = "Just Cute.";
    private int mana;
    private boolean canUsePower = true;
    private Player owner;
    ManaStrategy manaStrat;

    public StdHero(Player owner, ManaStrategy ms){
       this.owner = owner;
       manaStrat = ms;
       mana = ms.initialMana();
    }

    @Override
    public int getMana() {
        return mana;
    }

    public void setUsePower(boolean status){
        canUsePower = status;
    }

    public void setMana(int changeMana){
        mana = mana - changeMana;
    }

    public void refillMana(int turnNumber){
        mana = manaStrat.manaCalculator(turnNumber);
    }

    public void setHealth(int change){
        health = health-change;
    }

    @Override
    public int getHealth() {
        return health;
    }

    @Override
    public boolean canUsePower() {
        return canUsePower;
    }

    @Override
    public String getType() {
        return heroType;
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getEffectDescription() {
        return effectDesc;
    }

}
