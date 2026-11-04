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

/** Cards and minion figures is a composite figure, whose
 * parts are the emblem, the mana, the health, etc.
 *
 * This enumeration defines the part types used.
 */
public enum CardFigurePartType {
  MANA_FIGURE,
  ATTACK_FIGURE,
  HEALTH_FIGURE,
  EMBLEM_FIGURE,
  ACTIVE_FIGURE, // the Z icon for active or not
}
