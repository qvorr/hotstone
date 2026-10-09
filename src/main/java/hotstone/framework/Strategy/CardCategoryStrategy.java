package hotstone.framework.Strategy;

public interface CardCategoryStrategy {
    String getType(String name);

    void triggerCategoryEffect();
}
