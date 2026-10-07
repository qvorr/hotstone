package hotstone.standard;

import hotstone.framework.Hero;
import hotstone.framework.Strategy.HeroStrategy;
import hotstone.framework.Strategy.ManaStrategy;
import hotstone.framework.Player;

public class StdHero implements Hero {
    private int health = GameConstants.HERO_MAX_HEALTH;
    private int mana;
    private boolean canUsePower = true;
    private final Player owner;
    ManaStrategy ms;
    HeroStrategy hs;

    public StdHero(Player owner, ManaStrategy ms, HeroStrategy hs){
       this.owner = owner;
       this.ms = ms;
       this.hs = hs;
       mana = ms.initialMana();

    }

    public void setUsePower(boolean status){
        canUsePower = status;
    }

    public void decreaseManaBy(int changeMana){
        mana = mana - changeMana;
    }

    public void refillMana(int turnNumber){
        mana = ms.manaCalculator(turnNumber);
    }

    public void decreaseHealthBy(int change){
        health = health-change;
    }

    @Override
    public int getMana() {
        return mana;
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
        return hs.getHeroType(owner);
    }

    @Override
    public Player getOwner() {
        return owner;
    }

    @Override
    public String getEffectDescription() {
        return hs.getHeroPowerEffectDescription(getType());
    }

}
