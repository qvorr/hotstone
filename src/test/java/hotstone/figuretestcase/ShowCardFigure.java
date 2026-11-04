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

package hotstone.figuretestcase;

import hotstone.doubles.SimpleViewFactory;
import hotstone.doubles.StubCard;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import hotstone.view.figure.CardFigure;
import hotstone.view.figure.HotStoneFigureType;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.SelectionTool;

import java.awt.*;

/** Visual demonstration of the Figure depicting a card in the player's hand. */

public class ShowCardFigure {
  public static void main(String[] args) {
    DrawingEditor editor =
      new MiniDrawApplication( "Showing CardFigure, can be moved by selection tool...",
                               new SimpleViewFactory() );
    editor.open();
    Figure card1 = new CardFigure(HotStoneFigureType.CARD_FIGURE,
            new StubCard(GameConstants.UNO_CARD, Player.FINDUS), new Point(100, 100));
    Figure card2 = new CardFigure(HotStoneFigureType.CARD_FIGURE,
            new StubCard(GameConstants.BROWN_RICE_CARD, Player.FINDUS,
            7, "Eat 1 Cake."), new Point(280, 100));
    // Demo of a card which has not graphical image associated
    Figure card3 = new CardFigure(HotStoneFigureType.CARD_FIGURE,
            new StubCard("Fiskedeller", Player.FINDUS,
            8, "Do Stuff."), new Point(460, 100));

    // Demo of a card with long effect text
    Figure card4 = new CardFigure(HotStoneFigureType.CARD_FIGURE,
            new StubCard(GameConstants.PUMPKIN_SOUP_CARD, Player.FINDUS,8,
            "Give a minion Taunt and +2/+2."), new Point(100, 360));
    Figure card5 = new CardFigure(HotStoneFigureType.CARD_FIGURE, new StubCard("Power Wild", Player.FINDUS,
            8,
            "Give your minions +1/+1 or Summon a 3/2 Panther"), new Point(280, 360));


    editor.drawing().add(card1);
    editor.drawing().add(card2);
    editor.drawing().add(card3);
    editor.drawing().add(card4);
    editor.drawing().add(card5);
    editor.setTool(new SelectionTool(editor));
  }
}
