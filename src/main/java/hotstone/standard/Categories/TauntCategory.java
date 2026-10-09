package hotstone.standard.Categories;

import hotstone.framework.Strategy.CardCategoryStrategy;

public class TauntCategory implements CardCategoryStrategy {
    @Override
    public String getType(String name) {
        return "Taunt";
    }

    @Override
    public void triggerCategoryEffect() {
        // do nothing
    }
}
