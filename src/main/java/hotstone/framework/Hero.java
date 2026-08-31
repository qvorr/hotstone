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

/** The role of a Hero in HotStone. The present interface presents a
 * read-only view of the hero to respect that a client may only alter
 * the internal state of a hero through the Game's mutator methods.
 */
public interface Hero {
  /** Get the amount of mana this hero has available
   * for playing cards to the field.
   *
   * @return amount of mana available
   */
  int getMana();

  /** Get the value of health of this hero.
   *
   * @return the value of health
   */
  int getHealth();

  /** Get the state of the Hero's ability to use
   * his/her hero power. If true, the hero has not
   * yet used the hero power in this turn.
   * @return true if the hero is able to use the hero power,
   * false otherwise
   */
  boolean canUsePower();

  /** Get the type of the hero. Type is a
   * string value to be open for new hero types.
   * Default hero types are defined in GameConstants.
   *
   * @return the type of hero
   */
  String getType();

  /** Get the owner of this hero.
   *
   * @return the player having this hero
   */
  Player getOwner();

  /** Get the text describing the hero's power
   * @return short description of hero power/effect
   */
  String getEffectDescription();
}
