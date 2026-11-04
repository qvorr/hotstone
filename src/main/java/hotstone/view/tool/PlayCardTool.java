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

import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.framework.Status;
import hotstone.view.GfxConstants;
import hotstone.view.figure.CardFigure;
import minidraw.framework.*;
import minidraw.standard.NullTool;

import java.awt.event.MouseEvent;

/** Almost complete implementation of PlayCardTool: a MiniDraw tool
 * to play a card.
 */

// TODO: Finish the implementation of PlayCard tool
public class PlayCardTool extends NullTool {
  private DrawingEditor editor;
  private Game game;
  private CardFigure draggedActor;
  private int lastX;
  private int lastY;
  private int orgX;
  private int orgY;
  private Player whoAmIPlaying;

  public PlayCardTool(DrawingEditor editor, Game game, Player whoAmIPlaying) {
    this.editor = editor;
    this.game = game;
    this.whoAmIPlaying = whoAmIPlaying;
  }

  @Override
  public void mouseDown(MouseEvent e, int x, int y) {
    Drawing model = editor.drawing();
    // Note: The HotSeatStateTool should ensure that this tool
    // is only active iff there is a card figure below (x,y)
    Figure figureAtPosition = model.findFigure(e.getX(), e.getY());
    draggedActor = (CardFigure) figureAtPosition;
    // Move the card to the visual top
    model.zOrder(draggedActor, ZOrder.TO_TOP);
    // And remember where the card was dragged from (orgX, orgY)
    lastX = x; lastY = y;
    orgX = x; orgY = y;
  }

  @Override
  public void mouseDrag(MouseEvent e, int x, int y) {
    // compute relative movement
    draggedActor.moveBy(x - lastX, y - lastY);
    // update last position
    lastX = x; lastY = y;
  }

  @Override
  public void mouseUp(MouseEvent e, int x, int y) {
    // Invoke related facade method, if figure is a card
    boolean isDraggingAnActor = draggedActor != null;
    // are we dropping the card on the battlefield
    boolean isHittingField = y < GfxConstants.Y_LIMIT_OF_FIELD;
    // moveCardBack is true in case our card drag failed
    boolean moveCardBack = true;

    if (isDraggingAnActor && isHittingField) {
      Card associatedCard = draggedActor.getAssociatedCard();
      // TODO: Do the actual call instead of this fake code
      System.out.println("TODO: Do the actual call to the domain code...");
      Status status = Status.NOT_ENOUGH_MANA;
      if (status == Status.OK) {
        moveCardBack = false;
      }
      editor.showStatus("Draw card from hand. Result =" + status);
    }
    if (moveCardBack) {
      // move the dragged card back to original position; we have
      // to do this in the UI because there is no event originating
      // from the game via the observer that tell the GUI about
      // a failed playCard.
      draggedActor.moveBy(orgX - x, orgY - y);
    }
    draggedActor = null;
  }
}
