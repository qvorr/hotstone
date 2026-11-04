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

import hotstone.framework.Card;
import hotstone.view.GfxConstants;
import minidraw.framework.Figure;
import minidraw.standard.CompositeFigure;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;

import static java.util.Map.entry;

/**
 * A MiniDraw figure which associates with a Card/Minion to closely portrait
 * the attributes of the card in a graphical representation.
 * <p>
 * Note that as there are only two variants of the figure (one for
 * card, one for minion), a parametric design has been chosen. However,
 * as a Card (the domain abstraction) does not know if it is either,
 * this information has to be provided by the client through the
 * 'type' parameter for the constructor. Thus, for a Card to
 * become a Minion, you have to delete the figure for the
 * card and then create a figure for the minion, as is done
 * in HotStoneDrawing.
 * <p>
 * Developed during the year 2024 refactoring of the Figures for
 * cards.
 */
public class CardFigure extends CompositeFigure
        implements HotStoneFigure {
  /** The card that this figure represents graphically */
  private final Card associatedCard;
  /** The card type (card in hand OR minion on field) to
   * represent graphically.
   */
  private final HotStoneFigureType type;

  // Sub figures that must be manipulated as associated
  // card changes state
  private Figure frame;
  private Figure inactiveImage;

  // The text labels on the card
  private TextFigure healthText;
  private TextFigure attackText;
  private TextFigure nameText;

  // Mapping the individual parts to their graphical offsets
  private final Map<CardFigurePartType, Point> positions;

  // TODO: If you have implemented ThetaStone (with card categories),
  // you need to change this strategy to one that can compute
  // the proper gfx name of the relevant frame. The relevant
  // String constants are in GfxConstants ending with FRAME_IMAGE_NAME.
  // This is actually an 'inline' strategy pattern, but for simplicity
  // it is declared as an inline field that you may change-by-addition.
  private BiFunction<HotStoneFigureType, Card, String>
          computeFrameImageNameStrategy =
          (type, card) -> {
            // First the card types - spell or default
            if (type == HotStoneFigureType.CARD_FIGURE) {
              if (false /* Replace with test for card is SPELL card */) {
                return GfxConstants.SPELL_CARD_FRAME_IMAGE_NAME;
              } else {
                return GfxConstants.DEFAULT_CARD_FRAME_IMAGE_NAME;
              }
            } else { // Next the minion types - taunt or default
              if (false /* Replace with test for card is TAUNT card */) {
                return GfxConstants.TAUNT_MINION_FRAME_IMAGE_NAME;
              } else {
                return GfxConstants.DEFAULT_MINION_FRAME_IMAGE_NAME;
              }
            }};


  /**
   * Construct an 'actor' = a moving MiniDraw Figure
   * representing a card or a minion.
   *
   * @param type           the type of actor (card/minion)
   * @param associatedCard the associated card instance in
   *                       game
   * @param position       (x,y) of the actor in absolute window coordinates
   */
  public CardFigure(HotStoneFigureType type, Card associatedCard,
                    Point position) {
    this.type = type;
    this.associatedCard = associatedCard;

    // Compute what gfx to use for the frame of card/minion/taunt/spell
    String frameImageName = computeFrameImageNameStrategy.apply(type, associatedCard);
    // Pick the position according to if card is a minion or not
    if (type == HotStoneFigureType.CARD_FIGURE) {
      positions = cardFigurePartPositions;
    } else {
      positions = minionFigurePartPositions;
    }

    // Create the inactive image
    Point activePos = computePosOfInactiveImage();
    inactiveImage = new QuarterImageFigure(GfxConstants.INACTIVE_IMAGE_NAME,
            new Point(activePos));

    // Now, add all the sub figures to this composite, for all the
    // relevant parts: mana text, health text, frame, emblem, etc.
    writeLock().lock();
    try {
      addEmblemFigure(position);

      addFrameFigure(position, frameImageName);

      // Cards also show mana and potentially effect description
      if (type == HotStoneFigureType.CARD_FIGURE) {
        addManaFigure(associatedCard, position);
        if (associatedCard.getEffectDescription().length() > 0)
          addEffectFigure(associatedCard, position);
      }
      // Spell cards do not show attack/health
      if (! computeFrameImageNameStrategy.apply(type, associatedCard).equals(GfxConstants.SPELL_CARD_FRAME_IMAGE_NAME)) {
        addAttackFigure(associatedCard, position);
        addHealthFigure(associatedCard, position);
      }
      // Add the 'active' Z figure
      addActiveFigure(position);

    } finally {
      writeLock().unlock();
    }
  }

  private Point computePosOfInactiveImage() {
    Point activePos = (Point) displayBox().getLocation().clone();
    activePos.translate(positions.get(CardFigurePartType.ACTIVE_FIGURE).x,
            positions.get(CardFigurePartType.ACTIVE_FIGURE).y);
    return activePos;
  }

  // Create the active icon (green Z) only if it is a minion
  private void addActiveFigure(Point position) {
    if (getType() == HotStoneFigureType.MINION_FIGURE) {
      if (!associatedCard.isActive()) {
        Point p = computePosOfInactiveImage();
        inactiveImage.moveTo(p.x, p.y);
        add(inactiveImage);
      }
    }
  }

  private void addHealthFigure(Card associatedCard, Point position) {
    // Add health gfx
    Point healthPos = (Point) position.clone();
    healthPos.translate(positions.get(CardFigurePartType.HEALTH_FIGURE).x,
            positions.get(CardFigurePartType.HEALTH_FIGURE).y);
    healthText = new TextFigure("" + associatedCard.getHealth(),
            healthPos, Color.YELLOW, GfxConstants.SMALL_FONT_SIZE);
    add(healthText);
  }

  private void addAttackFigure(Card associatedCard, Point position) {
    // Add attack gfx
    Point attackPos = (Point) position.clone();
    attackPos.translate(positions.get(CardFigurePartType.ATTACK_FIGURE).x,
            positions.get(CardFigurePartType.ATTACK_FIGURE).y);
    attackText = new TextFigure("" + associatedCard.getAttack(),
            attackPos, Color.WHITE, GfxConstants.SMALL_FONT_SIZE);
    add(attackText);
  }

  private void addEffectFigure(Card associatedCard, Point position) {
    // Add the Effect labels, potentially over multiple text figures
    List<String> theTexts = breakTextOnWordBoundaryOnLimit(associatedCard.getEffectDescription(), 13);
    int dy = GfxConstants.EFFECT_Y_OFFSET - 6 * theTexts.size();
    int fontSize = theTexts.size() > 3 ? GfxConstants.EFFECT_FONT_SIZE_SMALL : GfxConstants.EFFECT_FONT_SIZE_LARGE;
    for (int i = 0; i < theTexts.size(); i++) {
      Point effectPos = (Point) position.clone();
      effectPos.translate(GfxConstants.EFFECT_X_OFFSET, dy + i * fontSize);
      TextFigure effectText = new TextFigure(theTexts.get(i),
              effectPos, Color.WHITE,
              fontSize);
      add(effectText);
    }
  }

  private void addManaFigure(Card associatedCard, Point position) {
    // Add the Mana label
    Point manaPos = (Point) position.clone();
    manaPos.translate(positions.get(CardFigurePartType.MANA_FIGURE).x,
            positions.get(CardFigurePartType.MANA_FIGURE).y);
    TextFigure manaText = new TextFigure("" + associatedCard.getManaCost(),
            manaPos, Color.WHITE, 16);
    add(manaText);
  }

  private static final String FRAME_TAG_NAME = "frame-image";
  private void addFrameFigure(Point position, String frameImageName) {
    // Add frame on top
    frame = new QuarterImageFigure(frameImageName, position);
    // Important - add this image with a TAG, allowing us to single it
    // out in the composite in order to change it (changing frames of
    // the minion)
    add(frame, FRAME_TAG_NAME);
  }

  @Override
  protected void basicMoveBy(int dx, int dy) {
    super.basicMoveBy(dx, dy);
    // As the inactiveImageFigure is not in the
    // super class' figure collection if the
    // minion is active, we have to move it manually.
    if (associatedCard.isActive()) {
      inactiveImage.moveBy(dx, dy);
    }
  }

  @Override
  public void draw(Graphics g) {
    super.draw(g);

    // Debug gfx: turn on the bounding box
    /*
    g.setColor(Color.ORANGE);
    g.drawRect(displayBox().x, displayBox().y,
            displayBox().width - 1, displayBox().height - 1); */
  }

  public Card getAssociatedCard() {
    return associatedCard;
  }

  @Override
  public HotStoneFigureType getType() {
    return type;
  }

  /**
   * Redraw health etc
   */
  public void updateStats() {
    writeLock().lock();
    try {
      // Only minions show active/passive gfx and may change
      // frames.
      if (getType() == HotStoneFigureType.MINION_FIGURE) {
        updateActiveGfx();
        updateMinionFrameGfx();
      }
      updateAttackGfx();
      updateHealthGfx();
    } finally {
      writeLock().unlock();
    }
  }

  /** Add the Gfx of the emblem - the core graphical image,
   * like the dish images of most interesting cards. If no
   * MiniDraw image file match the name of the associated
   * card, a background with text is shown instead.
   * @param position relative position of the image wrt
   *                 the actor.
   */

  private void addEmblemFigure(Point position) {
    // Add minion emblem
    Point emblemPos = (Point) position.clone();
    emblemPos.translate(positions.get(CardFigurePartType.EMBLEM_FIGURE).x,
            positions.get(CardFigurePartType.EMBLEM_FIGURE).y);
    QuarterImageFigure emblemFigure = new QuarterImageFigure(associatedCard.getName(), emblemPos);
    add(emblemFigure);

    // Iff the quarterimage has no associated graphical emblem
    if (!emblemFigure.hasAssociateImage()) {
      // Add card's name as text gfx over the emblem
      // Compute the position of the text - rather wonky :(
      Point textPos = (Point) position.clone();
      if (type == HotStoneFigureType.MINION_FIGURE) {
        textPos.translate(GfxConstants.DEFAULT_EMBLEM_TEXT_X_ON_MINION,
                GfxConstants.DEFAULT_EMBLEM_TEXT_Y);
      } else {
        textPos.translate(GfxConstants.DEFAULT_EMBLEM_TEXT_X_ON_CARD,
                GfxConstants.DEFAULT_EMBLEM_TEXT_Y);
      }
      String cardName = associatedCard.getName();
      nameText = new TextFigure(""
              + cardName
              .substring(0, cardName.length() > 10 ? 10 : cardName.length()),
              textPos, Color.YELLOW, GfxConstants.SMALL_FONT_SIZE);
      add(nameText);
    } else {
      nameText = null;
    }
  }

  /**
   * Update the frame gfx, for instance adding/removing
   * taunt.
   */
  private void updateMinionFrameGfx() {
    // Change the frame for Taunt / not Taunt
    Figure f = getByTag(FRAME_TAG_NAME);
    // We know that this tagged image is a QuarterImage, so casting is safe
    QuarterImageFigure quarterImageFigure = (QuarterImageFigure) f;
    // Get the name of the image that is currently shown
    String currentImageName = quarterImageFigure.getImageName();
    // Compute the name of the minion frame (taunt or not)
    String wantedImageName = computeFrameImageNameStrategy.apply(getType(), associatedCard);
    // If they do not match we must flip that image
    if (!currentImageName.equals(wantedImageName)) {
      quarterImageFigure.set(wantedImageName);
    }
  }

  /**
   * Update gfx showing if minion is active or not (the 'Z')
   */
  private void updateActiveGfx() {
    // Add/Remove the 'ZZZ' active/not active gfx
    if (!associatedCard.isActive()) {
      Point p = computePosOfInactiveImage();
      inactiveImage.moveTo(p.x, p.y);
      add(inactiveImage);
    } else {
      remove(inactiveImage);
    }
  }

  /**
   * Update gfx showing health (blood icon overlay)
   */
  private void updateHealthGfx() {
    healthText.setText("" + associatedCard.getHealth());
  }

  /**
   * Update gfx showing health (yellow blob icon overlay)
   */
  private void updateAttackGfx() {
    attackText.setText("" + associatedCard.getAttack());
  }


  /** Given a text, break it into lines of max n characters on
   * all word boundaries (spaces).
   * @param text the text to break
   * @param n max characters of each line
   * @return text broken into multiple lines
   */
  public static List<String> breakTextOnWordBoundaryOnLimit(String text, int n) {
    String[] words = text.split(" ");
    List<String> result = new ArrayList<>();
    int iResult = 0; result.add(words[0]);
    for (int i = 1; i < words.length; i++) {
      String line = result.get(iResult);
      if (line.length() + words[i].length() < n) {
        line = line + " " + words[i];
        result.set(iResult, line);
      } else {
        iResult += 1; result.add(words[i]);
      }
    }
    return result;
  }

  // Definition of graphical offsets of all the graphical parts of a Card: where is
  // the mana text, the emblem, the health text, etc.
  private static Map<CardFigurePartType, Point> cardFigurePartPositions = Map.ofEntries(
          entry(CardFigurePartType.MANA_FIGURE, new Point(19, 18)),
          entry(CardFigurePartType.ATTACK_FIGURE, new Point(20, 188 + 22)),
          entry(CardFigurePartType.HEALTH_FIGURE, new Point(149, 190 + 22)),
          entry(CardFigurePartType.EMBLEM_FIGURE, new Point(41, 6)),
          entry(CardFigurePartType.ACTIVE_FIGURE, new Point(0, 0))); // Not used

  // Definition of offsets for the minion
  private static Map<CardFigurePartType, Point> minionFigurePartPositions = Map.ofEntries(
          entry(CardFigurePartType.MANA_FIGURE, new Point(0, 0)), // Not used
          entry(CardFigurePartType.ATTACK_FIGURE, new Point(20, 106)),
          entry(CardFigurePartType.HEALTH_FIGURE, new Point(90, 106)),
          entry(CardFigurePartType.EMBLEM_FIGURE, new Point(10, 9)),
          entry(CardFigurePartType.ACTIVE_FIGURE, new Point(52, 110)));
}
