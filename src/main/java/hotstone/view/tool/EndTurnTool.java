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

package hotstone.view.tool;

import hotstone.framework.Game;
import hotstone.view.core.HotStoneDrawing;
import hotstone.view.figure.HotStoneFigure;
import hotstone.view.figure.HotStoneFigureType;
import minidraw.framework.DrawingEditor;
import minidraw.framework.Figure;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** MiniDraw Tool to change player/end turn. It works on both
 * the 'end turn' button and the 'swap player' button.
 * */
public class EndTurnTool extends NullTool {
  protected final HotStoneDrawing hotStoneDrawing;
  private Game game;

  public EndTurnTool(DrawingEditor editor, Game game) {
    // Note - we need access to the specialized 'hotseat'
    // behaviour, to swap UI in the model, alas a hard
    // coupling, made by casting...
    hotStoneDrawing = (HotStoneDrawing) editor.drawing();
    this.game = game;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {}

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    // Find the button below
    Figure figureAtPosition = hotStoneDrawing.findFigure(e.getX(), e.getY());
    if (figureAtPosition instanceof HotStoneFigure) {
      HotStoneFigure hsf = (HotStoneFigure) figureAtPosition;
      // Now, either 'end turn' is hit or 'swap player'
      if (hsf.getType() == HotStoneFigureType.TURN_BUTTON) {
        // Tell the game to end the turn
        game.endTurn();
        // The HotStoneDrawing will receive a 'onTurnChangeTo' event
        // which will force the drawing into a 'swap player' mode,
        // showing the 'swap button'.
      } else if (hsf.getType() == HotStoneFigureType.SWAP_BUTTON) {
        // The 'swap player button' has been hit - as this is a pure
        // GUI state change, we will need to tell the HotStoneDrawing
        // directly - it is not a state change that goes through the game
        // instance
        hotStoneDrawing.endHotSeatState();
      }
    }
  }
}
