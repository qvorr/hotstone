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

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

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
    game = new StandardHotStoneGame(new AlphaStoneStrategy());
  }

  // Turn handling
  @Test
  public void shouldHaveFindusAsFirstPlayer() {
    Player player = game.getPlayerInTurn();
    assertThat(player, is(Player.FINDUS));
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
  public void fieldSizeShouldReturn1afterPlayedCard(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 0), 0);
    assertThat(game.getFieldSize(Player.FINDUS), is(1));
  }

  @Test
  public void drawANewCardOnBeginningOfTurn(){
    for(int i = 3; i < 7; i++){
      assertThat(game.getHandSize(Player.FINDUS), is(i));
      game.endTurn();
      game.endTurn();
    }
  }

  @Test
  public void heroManaResetsTo3OnANewTurn(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    game.endTurn();
    game.endTurn();
    assertThat(game.getHero(Player.FINDUS).getMana(), is(3));
  }

  @Test
  public void heroEffectDescriptionShouldReturnBabyDescription(){
    assertThat(game.getHero(Player.FINDUS).getEffectDescription(), is("Just Cute."));
  }

  @Test
  public void heroOwnerShouldReturnCorrectPlayer(){
    assertThat(game.getHero(Player.FINDUS).getOwner(), is(Player.FINDUS));
  }

  @Test
  public void bothPlayersHave4CardDeckOnANewGame(){
    assertThat(game.getDeckSize(Player.FINDUS), is(4));
    assertThat(game.getDeckSize(Player.PEDDERSEN), is(4));
  }

  @Test
  public void playCardShouldReducePlayersMana(){
    int prevHeroMana = game.getHero(Player.FINDUS).getMana();
    Card playedCard = game.getCardInHand(Player.FINDUS, 2);
    game.playCard(Player.FINDUS, playedCard , 0);
    assertThat(game.getHero(Player.FINDUS).getMana(), is(prevHeroMana- playedCard.getManaCost()));
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
  public void cardBecomesActiveTheTurnAfterBeingPlayed(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 0), 0);
    assertThat(game.getCardInField(Player.FINDUS, 0).isActive(), is(false));
    game.endTurn();
    game.endTurn();
    assertThat(game.getCardInField(Player.FINDUS, 0).isActive(), is(true));
  }

  @Test
  public void inactiveMinionCannotAttack(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 0), 0);
    game.endTurn();
    assertThat(game.attackCard(Player.PEDDERSEN,
            game.getCardInHand(Player.PEDDERSEN, 0),
            game.getCardInField(Player.FINDUS, 0)),
            is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
  }

  @Test
  public void cannotAttackOwnMinion(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 1), 0);
    game.endTurn();
    game.endTurn();
    assertThat(game.attackCard(Player.FINDUS,
            game.getCardInField(Player.FINDUS, 0),
            game.getCardInField(Player.FINDUS, 1)),
            is(Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION));
  }

  @Test
  public void cannotAttackWithACardPlayedDoesntOwn(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    game.endTurn();
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
    assertThat(game.attackCard(Player.PEDDERSEN,
            game.getCardInField(Player.FINDUS, 0),
            game.getCardInField(Player.PEDDERSEN, 0)),
            is(Status.NOT_OWNER));
  }

  @Test
  public void cannotAttackWhenNotPlayersTurn(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    game.endTurn();
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 1), 0);
    game.endTurn();
    assertThat(game.attackCard(Player.PEDDERSEN,
            game.getCardInField(Player.PEDDERSEN, 0),
            game.getCardInField(Player.FINDUS, 0)),
            is(Status.NOT_PLAYER_IN_TURN));
  }

  @Test
  public void attackingCardShouldReturnOKStatus(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    game.endTurn();
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 1), 0);
    game.endTurn();
    game.endTurn();
    assertThat(game.attackCard(Player.PEDDERSEN,
                    game.getCardInField(Player.PEDDERSEN, 0),
                    game.getCardInField(Player.FINDUS, 0)),
            is(Status.OK));
  }

  @Test
  public void cannotAttackWithSameCardTwiceInOneTurn(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 0), 0);
    game.endTurn();
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 1), 1);
    game.endTurn();
    game.attackCard(Player.FINDUS, game.getCardInField(Player.FINDUS, 0), game.getCardInField(Player.PEDDERSEN, 0));
    assertThat(game.attackCard(Player.FINDUS, game.getCardInField(Player.FINDUS, 0), game.getCardInField(Player.PEDDERSEN, 0)),is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
  }

  @Test
  public void whenDefendingCardDiesItShouldBeRemovedFromTheField(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 0), 0);
    game.endTurn();
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
    game.endTurn();
    game.attackCard(Player.FINDUS, game.getCardInField(Player.FINDUS, 0), game.getCardInField(Player.PEDDERSEN, 0));
    assertThat(game.getFieldSize(Player.PEDDERSEN), is(0));
  }

  @Test
  public void whenAttackingCardDiesItShouldBeRemovedFromTheField(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 0), 0);
    game.endTurn();
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
    game.endTurn();
    game.endTurn();
    game.attackCard(Player.PEDDERSEN, game.getCardInField(Player.PEDDERSEN, 0), game.getCardInField(Player.FINDUS, 0));
    assertThat(game.getFieldSize(Player.PEDDERSEN), is(0));
  }

// attacking hero  tests
  @Test
  public void cannotAttackHeroWhenNotPlayersTurn(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    game.endTurn();
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
    assertThat(game.attackHero(Player.FINDUS, game.getCardInField(Player.FINDUS, 0)), is(Status.NOT_PLAYER_IN_TURN));
  }

  @Test
  public void cannotAttackHeroWithOtherPlayersCard(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    game.endTurn();
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
    game.endTurn();
    assertThat(game.attackHero(Player.FINDUS, game.getCardInField(Player.PEDDERSEN, 0)), is(Status.NOT_OWNER));
  }

  @Test
  public void cannotAttackHeroWhenMinionNotActive(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    assertThat(game.attackHero(Player.FINDUS, game.getCardInField(Player.FINDUS, 0)), is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
  }

  @Test
  public void attackingHeroShouldGiveOKStatus(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    game.endTurn();
    game.endTurn();
    assertThat(game.attackHero(Player.FINDUS, game.getCardInField(Player.FINDUS, 0)), is(Status.OK));
  }

  @Test
  public void cannotAttackHeroTwiceInOneTurnWithSameCard(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);
    game.attackHero(Player.FINDUS, game.getCardInField(Player.FINDUS, 0));
    assertThat(game.attackHero(Player.FINDUS, game.getCardInField(Player.FINDUS, 0)), is(Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION));
  }

//  hero power tests

  @Test
  public void cannotUseHeroPowerWhenNotOnPlayersTurn(){
    assertThat(game.usePower(Player.PEDDERSEN), is(Status.NOT_PLAYER_IN_TURN));
  }

  @Test
  public void notEnoughManaForHeroPower(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 0), 0);
    assertThat(game.usePower(Player.FINDUS), is(Status.NOT_ENOUGH_MANA));
  }

  @Test
  public void usingHeroPowerShouldReturnOKStatus(){
    assertThat(game.usePower(Player.FINDUS), is(Status.OK));
  }

  @Test
  public void usingHeroPowerShouldDecreaseHeroMana(){
    int prevMana = game.getHero(Player.FINDUS).getMana();
    game.usePower(Player.FINDUS);
    assertThat(game.getHero(Player.FINDUS).getMana(), is(prevMana - GameConstants.HERO_POWER_COST));
  }

  @Test
  public void cardDosAttributes222(){
    card = new StdCard(GameConstants.DOS_CARD, Player.FINDUS);
    int[] attributes = {card.getHealth(), card.getAttack(), card.getManaCost()};
    assertThat(attributes, is(new int[]{2, 2, 2}));
  }

  @Test
  public void aftetUsingHeroPowerStatusShouldBeFalse(){
    game.usePower(Player.FINDUS);
    assertThat(game.getHero(Player.FINDUS).canUsePower(), is(false));
  }

//  game winner tests
  @Test
  public void winnerNormallyShouldReturnNull(){
    for(int i = 0; i < 3; i++){
      assertThat(game.getWinner(), is(nullValue()));
      TestHelper.advanceGameNRounds(game, 1);
    }
  }

  @Test
  public void findusShouldWinOnTurn8(){
    TestHelper.advanceGameNRounds(game, 4);
    assertThat(game.getWinner(), is(Player.FINDUS));
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

  @Test
  public void handFindusShouldReturnTresat0(){
    assertThat(game.getHand(Player.FINDUS).iterator().next(), is(game.getCardInHand(Player.FINDUS, 0)));
  }

  @Test
  public void handPeddersenShouldReturnTresat0(){
    assertThat(game.getHand(Player.PEDDERSEN).iterator().next(), is(game.getCardInHand(Player.PEDDERSEN, 0)));
  }

  @Test
  public void fieldFindusShouldReturnDosat0(){
    game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 1), 0);
    assertThat(game.getField(Player.FINDUS).iterator().next(), is(game.getCardInField(Player.FINDUS, 0)));
  }

  @Test
  public void fieldPeddersenShouldReturnUnoat0(){
    game.endTurn();
    game.playCard(Player.PEDDERSEN, game.getCardInHand(Player.PEDDERSEN, 2), 0);
    assertThat(game.getField(Player.PEDDERSEN).iterator().next(), is(game.getCardInField(Player.PEDDERSEN, 0)));
  }
}
