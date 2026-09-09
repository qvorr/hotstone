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

import hotstone.framework.*;
import hotstone.utility.TestHelper;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/** Template for your own ongoing TDD process.
 * Fill it out until you have covered all
 * requirements for the alpha stone game.
 */
public class TestAlphaStone {
  private Game game;
  private StdCard card;
  private StdHero hero;

  /** Fixture for AlphaStone testing. */
  @BeforeEach
  public void setUp() {
    game = new StandardHotStoneGame();
  }

  // Turn handling
  @Test
  public void shouldHaveFindusAsFirstPlayer() {
    Player player = game.getPlayerInTurn();
    assertThat(player, is(Player.FINDUS));
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
  public void turnNumberFor3Rounds(){
    for (int i = 0; i <= 2; i++){
      assertThat(game.getTurnNumber(), is(i));
      game.endTurn();
    }
  }

  @Test
  public void bothPlayersStartAt21Health(){
    assertThat(game.getHero(Player.FINDUS).getHealth(), is(GameConstants.HERO_MAX_HEALTH));
    assertThat(game.getHero(Player.PEDDERSEN).getHealth(), is(GameConstants.HERO_MAX_HEALTH));
  }

  @Test
  public void heroTypeShouldBeBaby(){
    assertThat(game.getHero(Player.FINDUS).getType(), is(GameConstants.BABY_HERO_TYPE));
  }

  @Test
  public void bothPlayersHave4CardDeck(){
    assertThat(game.getDeckSize(Player.FINDUS), is(4));
    assertThat(game.getDeckSize(Player.PEDDERSEN), is(4));
  }

  @Test
  public void playCardShouldReducePlayersMana(){
    Card playedCard = game.getCardInHand(Player.FINDUS, 2);
    game.playCard(Player.FINDUS, playedCard , 0);
    assertThat(game.getHero(Player.FINDUS).getMana(), is(3- playedCard.getManaCost()));
  }

  @Test
  public void playedCardShouldBeRemovedFromHandWhenPlayed(){
//    we save the hand size before and after playing a card, and check if after is smaller by 1
    int ogHandSize = game.getHandSize(Player.FINDUS);
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    int afterHandSize = game.getHandSize(Player.FINDUS);
    assertThat(afterHandSize, is(ogHandSize-1));
  }

  @Test
  public void playedCardShouldReturnOKStatus(){
//    when everything works normally, we should receive
//    the OK status from the playCard() accessor method
    assertThat(game.playCard(Player.FINDUS,
            game.getCardInHand(Player.FINDUS, 0), 0),
            is(Status.OK));
  }

  @Test
  public void cannotPlayCardWithoutEnoughMana(){
//    tres is played
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 0), 0);
//    this time we try to play dos, but the total mana cost of tres
//    and dos is bigger than available mana.
    assertThat(game.playCard(Player.FINDUS,
            game.getCardInHand(Player.FINDUS, 0), 1),
            is(Status.NOT_ENOUGH_MANA));
  }

  @Test
  public void cannotPlayCardWhenNotPlayerTurn(){
    assertThat(game.playCard(Player.PEDDERSEN,
            game.getCardInHand(Player.PEDDERSEN, 0), 0),
            is(Status.NOT_PLAYER_IN_TURN));
  }

  @Test
  public void cannotPlayCardOfAnotherPlayer(){
    assertThat(game.playCard(Player.FINDUS,
            game.getCardInHand(Player.PEDDERSEN, 0), 0),
            is(Status.NOT_OWNER));
  }

  @Test
  public void cardDosAttributes222(){
    card = new StdCard(GameConstants.DOS_CARD, Player.FINDUS);
    int[] attributes = {card.getHealth(), card.getAttack(), card.getManaCost()};
    assertThat(attributes, is(new int[]{2, 2, 2}));
  }

  @Test
  public void PedersonShouldStillHave3Cards() {
    //We made Findus play card DOS at index 0 (doesnt matter the index)
    Card card = new StdCard(GameConstants.DOS_CARD, Player.FINDUS);
    game.playCard(Player.FINDUS, card, 0);

    //Now we check if Peddersen still has 3 cards
    int count = game.getHandSize(Player.PEDDERSEN);
    assertThat(count, is(3));
  }

  @Test
  public void shouldHave3CardsTresAt0() {
    assertThat(game.getCardInHand(Player.FINDUS, 0).getName(), is ("Tres"));
  }


  @Test
  public void shouldHave3CardsDosAt1() {
    assertThat(game.getCardInHand(Player.FINDUS, 1).getName(), is ("Dos"));
  }

  @Test
  public void shouldHave3CardsUnoAt2() {
    assertThat(game.getCardInHand(Player.FINDUS, 2).getName(), is ("Uno"));
  }

  @Test
  public void getCardOwner(){
    Card card = new StdCard(GameConstants.UNO_CARD, Player.FINDUS);
    assertThat(card.getOwner(), is(Player.FINDUS));
  }

  @Test
  public void setCardName(){
    Card card = new StdCard(GameConstants.UNO_CARD, Player.FINDUS);
    assertThat(card.getName(), is(GameConstants.UNO_CARD));
  }

}
