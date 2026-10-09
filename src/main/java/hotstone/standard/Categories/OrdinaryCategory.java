package hotstone.standard.Categories;

import hotstone.framework.Strategy.CardCategoryStrategy;

public class OrdinaryCategory implements CardCategoryStrategy {
    @Override
    public String getType(String name) {
        return "Ordinary";
    }

    @Override
    public void triggerCategoryEffect() {
        // does nothing
    }
}
