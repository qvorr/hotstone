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

package hotstone.observer;

import hotstone.framework.Game;
import hotstone.framework.Player;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestObserver {
  @Test
  public void shouldFireEventsForUsePower() {
    // Given a test double for the game observer
    GameObserver observerDouble = new ObserverDouble();
    // Given a game
    Game game = null; // TODO: replace with your game implementation of choice
    // When Findus use the hero power
    game.usePower(Player.FINDUS);

    // Then a sequence of events are triggered/fired
    // from the game
    /*
      From FRS Chapter 37.6.1
      onUsePower(FINDUS) - due to the mutator being called
        onHeroUpdate(FINDUS) - due to hero spending mana
        onHeroUpdate(PEDDERSEN) - due to opponent health reduced
     */
    // TODO: Use the double to verify the proper sequence
    assertThat(true, is(true)); // Remove me!
  }

}

