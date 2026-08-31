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

/** The role of a single HotStone game, allowing clients to access and
 * modify the state of a game.
 * 
 */
public interface Game {
  // === Accessors for Game state

  /** Get the player who currently can make actions (call mutators) on
   * game.
   *
   * @return the player in turn.
   */
  Player getPlayerInTurn();

  /** Get the hero for the given player.
   * PRECONDITION: 'who' is never null.
   * 
   * @param who the owning player
   * @return the hero
   */
  Hero getHero(Player who);

  /** Get who has won the game.
   *
   * @return null if game is still progressing, or the winning player
   * if the game has been won.
   */
  Player getWinner();

  /** Get the number of turns played. Starts at turn 0 for Findus,
   * then 1 for Peddersen, 2 for Findus, and so on.
   *
   * @return the turn number of the game progress
   */
  int getTurnNumber();

  /** Get the number of cards still in the drawing deck for a given
   * player.
   * PRECONDITION: 'who' is never null.
   *
   * @param who the player whose deck to inspect
   * @return number of cards left in the deck
   */
  int getDeckSize(Player who);

  // === Accessors for Hand

  /** Get the card at a given index in the hand.  Index goes from 0 up
   * till 'getHandSize()-1'. A card drawn from the deck to the hand
   * will always enter into the hand at index 0, pushing all other
   * cards to +1 index.
   * 
   * PRECONDITION: 'who' is never null.
   * PRECONDITION: indexInHand MUST be in interval 0..handsize-1.
   *
   * @param who the player whose hand to inspect
   * @param indexInHand the index of the card to retrieve. MUST be
   *                    0..handsize-1.
   * @return the card in the hand at that position.
   */
  Card getCardInHand(Player who, int indexInHand);

  /** Get an iterable over the cards in the hand. Convenience method
   * to allow writing code ala 
   * 'for (Card c: game.getHand(Player.FINDUS)) { ... }'.
   *
   * Alternatively, if you want streaming:
   * StreamSupport.stream(game.getHand(who).spliterator(), false).
   *
   *
   * PRECONDITION: 'who' is never null.
   *
   * @param who the player owning the hand
   * @return an iterable over the cards in the hand
   */
  Iterable<? extends Card> getHand(Player who);

  /** Get the number of cards in the hand.
   *
   * PRECONDITION: 'who' is never null.
   *
   * @param who the player owning the hand
   * @return the number of cards in hand
   */
  int getHandSize(Player who);

  // === Accessors for Field

  /** Get the card at a given index on the field.  Index goes from 0
   * up till 'getFieldSize()-1'.
   *
   * PRECONDITION: 'who' is never null.
   * PRECONDITION: indexInField MUST be in interval 0..fieldsize-1.
   *
   * @param who the player whose field to inspect
   * @param indexInField the index of the card to retrieve. MUST be
   *                    0..fieldsize-1.
   * @return the card on the field at that position.
   */
  Card getCardInField(Player who, int indexInField);
  
  /** Get an iterable over the cards on the field. Convenience method
   * to allow writing code ala 
   * 'for (Card c: game.getField(Player.FINDUS)) { ... }'.
   *
   * Alternatively, if you want streaming:
   * StreamSupport.stream(game.getField(who).spliterator(), false).
   *
   * PRECONDITION: 'who' is never null.
   *
   * @param who the player owning the field
   * @return an iterable over the cards on the field
   */
  Iterable<? extends Card> getField(Player who);
  
  /** Get the number of cards on the field.
   *
   * PRECONDITION: 'who' is never null.
   *
   * @param who the player owning the field
   * @return the number of cards on field
   */
  int getFieldSize(Player who);

  // === Mutators

  /**
   * Perform end of turn for current player, to prepare for the
   * opponent's turn.  PRECONDITION: The client MUST ensure that
   * endTurn() is never called by the player which is NOT in turn.
   */
  void endTurn();

  /** Play a card from the hand to the field.
   *
   * PRECONDITION: 'who' is never null.
   * PRECONDITION: the card is a non-null card retrieved by the
   * 'getCardInHand()' method.
   * PRECONDITION: atIndex in range 0 .. getFieldSize(who)
   *
   * @param who player to play card
   * @param card card to play
   * @param atIndex index at which the card should be
   *                entered on the field. 0 = left of
   *                leftmost minion, 1 = between first
   *                and second minion, etc.
   * @return Status of operation
   */
  public Status playCard(Player who, Card card, int atIndex);

  /** Attack one card with another on the fields.
   *
   * PRECONDITION: 'who' is never null.
   * PRECONDITION: both cards are a non-null card retrieved by the
   * 'getCardInField()' method.
   *
   * @param playerAttacking the player making the attack
   * @param attackingCard the card attacking
   * @param defendingCard the card defending
   * @return a status identifying the outcome of the attack, which is
   *    either OK or some value explaining why the action was invalid.
   */
  Status attackCard(Player playerAttacking,
                    Card attackingCard, Card defendingCard);

  /** Attack a hero with a card in the field.
   *
   * PRECONDITION: 'who' is never null.
   * PRECONDITION: the card is a non-null card retrieved by the
   * 'getCardInField()' method.
   *
   * @param playerAttacking the player making the attack
   * @param attackingCard the card attacking
   * @return a status identifying the outcome of the attack, which is
   *    either OK or some value explaining why the action was invalid.
   */
  Status attackHero(Player playerAttacking, Card attackingCard);

  /** Use a hero's special power/effect.
   *
   * PRECONDITION: 'who' is never null.
   *
   * @param who the player using his/her hero's power
   * @return a status identifying the outcome of the power use, which
   *    is either OK or some value explaining why the action was
   *    invalid.
   */
  Status usePower(Player who);
}
