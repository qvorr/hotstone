package hotstone.framework;

public interface MutableCard extends Card{

    public void setHealth(int change);

    public void increaseAttack(int amount);

    public void setActive(boolean status);
}