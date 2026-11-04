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

import hotstone.view.GfxConstants;
import hotstone.view.figure.HotStoneFigure;
import minidraw.framework.Drawing;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/** A Helper class with a static method to compute the where cards are dropped with respect
 * to surrounding minions on the battlefield.
 */
public class DropZone {

  /** Given (x,y) of drop position of a card, and the current
   * MiniDraw drawing (with all figures) compute where that
   * card is dropped with respect to the already fielded minions that
   * belongs to the player.
   * @param drawing the MiniDrawDrawing
   * @param x X coordinate of dropped card
   * @param y Y coordinate of dropped card
   * @return an integer which encode which position relative to
   * the already fielded minions that this card is dropped to. The
   * encoding starts with 0 (left of first minion), 1 (between
   * first and second), 2 (between second and third), ..
   * #minions +1 (right of last minion). This is the same
   * encoding as used in the playCard method of Game.
   */
  public static int computeFieldIndexOfDropPosition(Drawing drawing, double x, double y) {
    // Create list of X coords of all my fielded minions
    List<Integer> listOfXCoordOfMyMinions = StreamSupport.stream(drawing.spliterator(), false)
            .filter(f -> f instanceof HotStoneFigure)
            .filter(f -> f.displayBox().y == GfxConstants.MY_FIELD_Y_POSITION)
            .map(f -> f.displayBox().x)
            .collect(Collectors.toList());
    return computeFieldIndexForDropAtX((int) x, listOfXCoordOfMyMinions);
  }

  /** Compute the index in the field where a minion should be inserted,
   * given it is dropped at a certain X coordinate.
   * @param dropX the X coordinate where the minion is dropped on the UI
   * @param listOfXPositionOfMinions a list of X positions of all fielded minions to consider
   * @return index in the Games' battlefield array where the minion should be inserted
   */
  static int computeFieldIndexForDropAtX(int dropX, List<Integer> listOfXPositionOfMinions) {
    // Sort it in ascending order of X, as they can be in random order
    List<Integer> sortedXCoords = listOfXPositionOfMinions.stream()
            .sorted()
            .collect(Collectors.toList());
    // Count the number of minions to the left of the drop X which is equal to the index
    int index = (int) sortedXCoords.stream()
            .filter(minionX -> minionX <= dropX)
            .count();
    return index;
  }
}
