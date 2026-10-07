package hotstone.framework;

public interface MutableHero extends Hero {
    void setUsePower(boolean status);

    void decreaseManaBy(int changeMana);

    void refillMana(int turnNumber);

    void decreaseHealthBy(int change);
}
