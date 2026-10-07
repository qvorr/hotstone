package hotstone.framework;

public interface MutableHero {
    public void setUsePower(boolean status);

    public void decreaseManaBy(int changeMana);

    public void refillMana(int turnNumber);

    public void decreaseHealthBy(int change);
}
