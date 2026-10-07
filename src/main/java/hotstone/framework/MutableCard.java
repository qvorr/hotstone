package hotstone.framework;

public interface MutableCard extends Card{

    void decreaseHealthBy(int change);

    void increaseAttack(int amount);

    void setActive(boolean status);

    void triggerEffect(MutableGame game);
}