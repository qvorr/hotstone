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

package hotstone.gui2domain;

import hotstone.doubles.FakeObjectGame;
import hotstone.framework.Game;
import hotstone.framework.Player;
import hotstone.view.core.HotStoneDrawingType;
import hotstone.view.core.HotStoneFactory;
import hotstone.view.tool.HotSeatStateTool;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

/** Visual 'test-driven' development of the MiniDraw tools that
 * allow GUI manipulations to be translated into HotStone game
 * mutator calls i.e. the 'From GUI to Domain' flow of events.
 *
 * Guided by your TestList, use this main program to incrementally
 * add more and more MiniDraw tools for each user interaction until
 * the full working UI is in place.
 */
public class ShowTools {
  public static void main(String[] args) {
    // TODO: Replace the below assignment into a stable and well
    // tested variant of HotStone, you probably want to start with
    // a simple variant, like AlphaStone
    Game game = new FakeObjectGame();

    DrawingEditor editor =
            new MiniDrawApplication( "Test-Driven Dev of Tools",
                    new HotStoneFactory(game, Player.FINDUS,
                            HotStoneDrawingType.HOTSEAT_MODE) );
    editor.open();
    editor.setTool(new HotSeatStateTool(editor, game));
  }
}
