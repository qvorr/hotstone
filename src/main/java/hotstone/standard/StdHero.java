package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Player;
import hotstone.framework.Strategy.StrategyMaster;
import hotstone.framework.Strategy.WinnerStrategy;

public class StdHero implements Hero {
    private String heroType = GameConstants.BABY_HERO_TYPE;
    private int health = GameConstants.HERO_MAX_HEALTH;
    private String effectDesc = "Just Cute.";
    private int mana;
    private boolean canUsePower = true;
    private Player owner;
    StrategyMaster strat;

    public StdHero(Player owner, StrategyMaster strat){
       this.owner = owner;
       this.strat  = strat;
       mana = strat.initialMana();

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
        mana = strat.manaCalculator(turnNumber);
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
        return strat.getHeroType(owner);
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
