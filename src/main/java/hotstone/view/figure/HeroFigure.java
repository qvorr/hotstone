/*
 * Copyright (C) 2022 - 2026. Henrik Bærbak Christensen, Aarhus University.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package hotstone.view.figure;

import hotstone.framework.Hero;
import hotstone.view.GfxConstants;
import minidraw.standard.CompositeFigure;

import java.awt.*;

/** MiniDraw Figure to represent the Hero.
 *
 */
public class HeroFigure extends CompositeFigure implements HotStoneFigure {
  private final QuarterImageFigure heroImage;
  private final TextFigure manaText;
  private final TextFigure healthText;
  private final Hero hero;
  private QuarterImageFigure zzzImage;

  private Point Zpos;
  private Rectangle displayBox;

  public HeroFigure(Hero hero, Point position) {
    super();
    this.hero = hero;
    writeLock().lock();
    try {
      heroImage = new QuarterImageFigure(hero.getType(), position);
      add(heroImage);

      QuarterImageFigure heroIconOverlay = new QuarterImageFigure("hero-frame", position);
      add(heroIconOverlay);

      // Adjust displaybox
      displayBox = new Rectangle(position.x, position.y,
              heroImage.displayBox().width, heroImage.displayBox().height);

      // Mana
      Point manaPos = (Point) position.clone();
      manaPos.translate(GfxConstants.HERO_MANA_ICON_OFFSET_X,
              GfxConstants.HERO_MANA_ICON_OFFSET_Y);

      manaText = new TextFigure("" + hero.getMana(),
              manaPos, Color.WHITE, GfxConstants.MEDIUM_FONT_SIZE);
      add(manaText);

      // Health
      Point healthPos = (Point) position.clone();
      healthPos.translate(GfxConstants.HERO_HEALTH_ICON_OFFSET_X, GfxConstants.HERO_HEALTH_ICON_OFFSET_Y);

      healthText = new TextFigure("" + hero.getHealth(),
              healthPos, Color.YELLOW, GfxConstants.MEDIUM_FONT_SIZE);
      add(healthText);

      // Power used or not
      Zpos = (Point) position.clone();
      Zpos.translate(GfxConstants.HERO_IS_NOT_ACTIVE_X, GfxConstants.HERO_IS_NOT_ACTIVE_Y);
      zzzImage = new QuarterImageFigure("Z", Zpos);

      updateStats();
    } finally {
      writeLock().unlock();
    }
  }

  @Override
  public HotStoneFigureType getType() {
    return HotStoneFigureType.HERO_FIGURE;
  }

  @Override
  public Rectangle displayBox() {
    return displayBox;
  }

  public Hero getAssociatedHero() {
    return hero;
  }

  public void updateStats() {
    writeLock().lock();
    try {
      manaText.setText("" + hero.getMana());
      healthText.setText("" + hero.getHealth());
      if (! getAssociatedHero().canUsePower()) {
        add(zzzImage);
      } else {
        remove(zzzImage);
      }
    } finally {
      writeLock().unlock();
    }
  }
}
