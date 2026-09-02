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

package hotstone.standard;

/**
 * Skeleton class for AlphaStone test cases
 *
 *    This source code is from the book
 *      "Flexible, Reliable Software:
 *        Using Patterns and Agile Development"
 *      2nd Edition
 *    Author:
 *      Henrik Bærbak Christensen
 *      Department of Computer Science
 *      Aarhus University
 */

import hotstone.framework.Card;
import hotstone.framework.Player;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import hotstone.framework.Game;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestAlphaStone {
  private Game game;
  private StdCard card;

  /** Fixture for AlphaStone testing. */
  @BeforeEach
  public void setUp() {
    game = new StandardHotStoneGame();
  }

  // Example of an early, simple test case:
  // Turn handling
  @Test
  public void shouldHaveFindusAsFirstPlayer() {
    // Given a game
    // When I ask for the player in turn
    Player player = game.getPlayerInTurn();
    // Then it should be Findus

    assertThat(player, is(Player.FINDUS));

    // Later on, the 'printGameState' can be very handy during development
    // as you can inspect the state of the game (the Given in GWT) and
    // ensure your assumptions are correct before modifying it (When)
    // to test a new feature (Then)

    // TestHelper.printGameState(game);
  }

  @Test
  public void shouldHavePeddersenAfterFindus(){
    game.endTurn();
    Player player = game.getPlayerInTurn();
    assertThat(player, is(Player.PEDDERSEN));
  }

  // Potentially redundant
  @Test
  public void shouldHaveFindusAfterPeddersen(){
    game.endTurn();
    game.endTurn();
    Player player = game.getPlayerInTurn();
    assertThat(player, is(Player.FINDUS));
  }

  @Test
  public void cardDosAttributes222(){
    card = new StdCard(GameConstants.DOS_CARD);
    int[] attributes = {card.getHealth(), card.getAttack(), card.getManaCost()};
    assertThat(attributes, is(new int[]{2, 2, 2}));
  }

  @Test
  public void turnNumberFor3Rounds(){
    for (int i = 1; i <= 3; i++){
      assertThat(game.getTurnNumber(), is(i));
      game.endTurn();
    }
  }

  @Test
  public void PedersonShouldStillHave3Cards() {
    //We made Findus play card DOS at index 0 (doesnt matter the index)
    Card card = new StdCard(GameConstants.DOS_CARD);
    game.playCard(Player.FINDUS, card, 0);

    //Now we check if Peddersen still has 3 cards
    int count = game.getHandSize(Player.PEDDERSEN);
    assertThat(count, is(3));
  }



}
