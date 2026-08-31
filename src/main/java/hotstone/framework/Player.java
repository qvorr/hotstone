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

package hotstone.framework;

/** Enum representing the two players in the game.
 * Also has the 'find the opposite player' method.
 * */

public enum Player {
  /** The Findus player. */
  FINDUS,
  /** The Peddersen player. */
  PEDDERSEN;

  /** Compute 'the other' player
   *
   * @param who am I
   * @return who my opponent is
   */
  public static Player computeOpponent(Player who) {
    return who == Player.FINDUS ? Player.PEDDERSEN : Player.FINDUS;
  }
}
// Credits: Thanks to Anders Kristoffersen, SWEA 2023 student, for pointing
// out that the computeOpponent method belongs here.
