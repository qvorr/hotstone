package hotstone.framework;

public interface MutableCard {

    public void setHealth(int change);

    public void increaseAttack(int amount);

    public void setActive(boolean status);
}