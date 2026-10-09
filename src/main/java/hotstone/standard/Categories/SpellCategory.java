package hotstone.standard.Categories;

import hotstone.framework.Strategy.CardCategoryStrategy;

public class SpellCategory implements CardCategoryStrategy {
    @Override
    public String getType(String name) {
        return "Spell";
    }

    @Override
    public void triggerCategoryEffect() {
        // do nothing
    }
}
