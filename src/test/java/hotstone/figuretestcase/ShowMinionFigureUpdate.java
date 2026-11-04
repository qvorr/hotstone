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
import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.standard.GameConstants;
import hotstone.view.figure.CardFigure;
import hotstone.view.figure.HotStoneFigureType;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;
import minidraw.standard.NullTool;

import java.awt.*;
import java.awt.event.MouseEvent;

/** Demonstration of updating the attack, health, and active values
 * of a minion.
 */

public class ShowMinionFigureUpdate {

  public static void main(String[] args) {
    DrawingEditor editor =
      new MiniDrawApplication( "Showing Minions; Update stats of fries by clicking anywhere...",
                               new SimpleViewFactory() );
    editor.open();

    // Create cards
    StubCard frenchFries = new StubCard(GameConstants.FRENCH_FRIES_CARD,
            Player.FINDUS, 1);
    frenchFries.setActiveTo(true);
    Card deller = new StubCard("Fiskedelle", Player.FINDUS, 2);

    // Create minion figures
    CardFigure mf1 = new CardFigure(HotStoneFigureType.MINION_FIGURE, frenchFries, new Point(200,200));
    CardFigure mf2 = new CardFigure(HotStoneFigureType.MINION_FIGURE, deller, new Point(400,200));

    editor.drawing().add(mf1);
    editor.drawing().add(mf2);


    editor.setTool( new UpdateMinionTool(editor, mf1) );
  }

  private static class UpdateMinionTool extends NullTool {
    private CardFigure aMinionFigure;

    public UpdateMinionTool(DrawingEditor editor, CardFigure aMinionFigure) {
      this.aMinionFigure = aMinionFigure;
    }

    @Override
    public void mouseUp(MouseEvent e, int x, int y) {
      StubCard ff = (StubCard) aMinionFigure.getAssociatedCard();
      ff.setActiveTo(! ff.isActive());
      ff.deltaHealth(+1);
      ff.deltaAttack(+2);
      // trigger UI update
      aMinionFigure.updateStats();
    }
  }
}
