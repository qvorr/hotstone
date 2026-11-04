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

package hotstone.view;

import java.awt.*;

/** A collection of graphical constants for the layout of
 * the UI of HotStone.
 */
public class GfxConstants {
  // Set the window/frame size. If you cannot have the UI on
  // your screen (the button cut off) then you may experiment
  // with lowering the HEIGHT value to, say, 700 or what-ever
  // works. If you change it, have a look at Y_FIELD_OFFSET
  // which may need to be adjusted to move minions further
  // up on the battlefield.
  public static final int SCREEN_WIDTH_PIXELS = 1100;
  public static final int SCREEN_HEIGHT_PIXELS = 840; // Normally 840

  // Position of central graphical actors - My own hero
  public static final Point MY_HERO_POSITION =
          new Point(SCREEN_WIDTH_PIXELS - 200,
          SCREEN_HEIGHT_PIXELS - 224);
  public static final Point MY_HERO_POWER_DESCRIPTION_POSITION =
          new Point(SCREEN_WIDTH_PIXELS - 200,
                  SCREEN_HEIGHT_PIXELS - 30);

  public static final int HERO_APPROXIMATE_LETTER_WIDTH_PIXEL = 10;

  // The offsets of the Hero's 'Z' text when inactive
  public static final int HERO_IS_NOT_ACTIVE_X = 90;
  public static final int HERO_IS_NOT_ACTIVE_Y = 170;

  // The offsets of the Hero's mana and health icons
  public static final int HERO_MANA_ICON_OFFSET_X = 25;
  public static final int HERO_MANA_ICON_OFFSET_Y = 186;
  public static final int HERO_HEALTH_ICON_OFFSET_X = 164;
  public static final int HERO_HEALTH_ICON_OFFSET_Y = 188;

  // Position of opponent hero
  public static final Point OPPONENT_HERO_POSITION = new Point(0, 0);
  public static final Point OPPONENT_SUMMARY_POSITION = new Point(180, 0);
  public static final Point OPPONENT_HERO_POWER_DESCRIPTION_POSITION = new Point(180, 20);


  // If y is above this limit, then the card play tool will consider the card
  // dropped upon the player's field (i.e. call game.playCard())
  public static final int Y_LIMIT_OF_FIELD = SCREEN_HEIGHT_PIXELS / 3 * 2;

  // The two font sizes used
  public static final int SMALL_FONT_SIZE = 16;
  public static final int MEDIUM_FONT_SIZE = 18;
  public static final int LARGE_FONT_SIZE = 20;

  // The distance between fielded minions
  public static final int FIELDED_CARD_DISTANCE = 150;

  // And distances in the hand
  public static final int HAND_CARD_DISTANCE = 100;
  public static final int HAND_CARD_OFFSET = 10;

  // Y coordinate of where my hand cards are positioned
  public static final int MY_HAND_POSITION_Y = SCREEN_HEIGHT_PIXELS - 240;

  // Minion positions on the field...

  // X offset used in calculating center position for minions
  public static final int X_FIELD_OFFSET = 30;
  // If you change on the HEIGHT of the display, you will likely also
  // tune the Y_FIELD_OFFSET to move the minions further up (negative offset)
  public static final int Y_FIELD_OFFSET = -50;
  // The Y coordinate of the minions of the field for me and opponent respectively
  public static final int MY_FIELD_Y_POSITION = SCREEN_HEIGHT_PIXELS / 2 + 50 + Y_FIELD_OFFSET;
  public static final int OPPONENT_FIELD_Y_POSITION = SCREEN_HEIGHT_PIXELS / 2 - 150 + Y_FIELD_OFFSET;

  // Text on buttons
  public static final String END_TURN_TEXT = "End Turn";
  public static final String NEXT_ACTION_TEXT = "Opp. Acts";
  // Button positions
  public static final Point TURN_BUTTON_POSITION = new Point(SCREEN_WIDTH_PIXELS - 200,20);  //  or y = 420
  public static final Point HOTSEAT_BUTTON_POSITION = new Point(SCREEN_WIDTH_PIXELS - 200,60);
  public static final Point WIN_BUTTON_POSITION = new Point(500, SCREEN_HEIGHT_PIXELS / 2);

  // Message system
  public static final int DISPLAY_TIME_MESSAGES_MS = 3000;
  public static final int MESSAGE_X = SCREEN_WIDTH_PIXELS - 580;
  public static final int MESSAGE_Y = 14;
  public static final int MESSAGE_HEIGHT = 28;
  // Maximum number of 'blue info boxes' that may appear, to
  // avoid growing all the down to the bottom of the window
  public static final int MAX_MESSAGES_TO_DISPLAY = 8;

  // Colors in the message system
  public final static Color MESSAGE_BLUE = new Color(0,123,198);
  public final static Color MESSAGE_SHADOW = new Color(70,88,101);

  // Colors used for field, text etc
  public static final Color YELLOW_COLOR = new Color(200,200,0);
  public static final Color GRAY_COLOR = Color.DARK_GRAY;
  public static final Color FIELD_COLOR_FINDUS = new Color(0, 77, 25);
  public static final Color FIELD_COLOR_PEDDERSEN = new Color(118, 65, 0);

  // Card Figures
  // If a QuarterImageFigure tries to load an image which is not present in the
  // 'minidraw-images' folder, it will default to a default emblem image
  // to avoid crashing, and ease developing new card types; and add the
  // card's name in the 'banner' - ugly and crude but workable for developing.
  public static final String DEFAULT_EMBLEM_IMAGE_NAME = "default-emblem";
  // The names of the card frame used for cards and minions, must be
  // identical to the jpg file names in the MiniDraw resource folder
  public static final String DEFAULT_MINION_FRAME_IMAGE_NAME = "frame";
  public static final String TAUNT_MINION_FRAME_IMAGE_NAME = "taunt-frame-sd";
  public static final String DEFAULT_CARD_FRAME_IMAGE_NAME = "card-basis";
  public static final String SPELL_CARD_FRAME_IMAGE_NAME = "card-spell";
  public static final String INACTIVE_IMAGE_NAME = "Z";

  // If the UI cannot find any emblem graphics matching the card's name,
  // then a text is show instead. These values determine offsets.
  public static final int DEFAULT_EMBLEM_TEXT_X_ON_CARD = 42;
  public static final int DEFAULT_EMBLEM_TEXT_X_ON_MINION = 12;
  public static final int DEFAULT_EMBLEM_TEXT_Y = 62;

  // Effect descriptions on the card: offsets and font sized
  public static final int EFFECT_X_OFFSET = 36;
  public static final int EFFECT_Y_OFFSET = 180;
  public static final int EFFECT_FONT_SIZE_LARGE = 14;
  public static final int EFFECT_FONT_SIZE_SMALL = 12;

  /** Given a minion at index 'indexOnField' fielded on a
   * battlefield of size 'fieldSize', compute the proper X
   * position of that minionFigure.
   * @param fieldSize size of the field (count of minions)
   * @param indexOnField which is my position (0..fieldSize-1)
   * @return the proper x position of the minion figure
   */
  public static int computeXForMinion(int fieldSize, int indexOnField) {
    int centerX =
            GfxConstants.SCREEN_WIDTH_PIXELS / 2
                    - (fieldSize % 2 == 0 ? 0 : GfxConstants.FIELDED_CARD_DISTANCE / 2)
                    - (fieldSize / 2) * GfxConstants.FIELDED_CARD_DISTANCE + GfxConstants.X_FIELD_OFFSET;
    return centerX + GfxConstants.FIELDED_CARD_DISTANCE * indexOnField;
  }
}
