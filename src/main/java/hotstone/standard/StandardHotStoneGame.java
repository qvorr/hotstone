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

import hotstone.framework.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** This is the 'temporary test stub' in TDD
 * terms: the initial empty but compilable implementation
 * of the game interface.
 *
 * It already includes a bit of FAKE-IT code for the first
 * test case about hand management and player in turn.
 *
 * Start solving the AlphaStone exercise by
 * following the TDD rhythm: pick a one-step-test
 * from your test list, quickly add a test,
 * run it to see it fail, and then modify this
 * implementing class (and supporting classes)
 * to make your test case run. Refactor and repeat.
 *
 * While this is the implementation of Game for
 * the AlphaStone game, you will constantly
 * refactor it over the course of the exercises
 * to become the 'core implementation' which will
 * enable a lot of game variants. This is also
 * why it is not called 'AlphaGame'.
 */

public class StandardHotStoneGame implements Game {
  private Player playerInTurn = Player.FINDUS;  // First player in turn is FINDUS
  private int turnNumber = 0;
  private Map<Player, List<StdCard>> hand = new HashMap<>();
  private Map<Player, StdHero> heroes = new HashMap<>();
  private Map<Player, List<StdCard>> field = new HashMap<>();
  private Map<Player, List<StdCard>> deck = new HashMap<>();

  private final String[] AlphaStoneDeck = {
          GameConstants.UNO_CARD,
          GameConstants.DOS_CARD,
          GameConstants.TRES_CARD,
          GameConstants.CUATRO_CARD,
          GameConstants.CINCO_CARD,
          GameConstants.SEIS_CARD,
          GameConstants.SIETE_CARD,
  };



  public StandardHotStoneGame() {
    field.put(Player.FINDUS, new ArrayList<>());
    field.put(Player.PEDDERSEN, new ArrayList<>());
    heroes.put(Player.FINDUS, new StdHero());
    heroes.put(Player.PEDDERSEN, new StdHero());
    hand.put(Player.FINDUS, new ArrayList<>());
    hand.put(Player.PEDDERSEN, new ArrayList<>());

    dealCards();

  }

  private void dealCards(){
//    Initialize the decks for both players
    deck.put(Player.FINDUS, new ArrayList<>());
    for(String name : AlphaStoneDeck){
      deck.get(Player.FINDUS).add(new StdCard(name, Player.FINDUS));
    }
    deck.put(Player.PEDDERSEN, new ArrayList<>());
    for(String name : AlphaStoneDeck){
      deck.get(Player.PEDDERSEN).add(new StdCard(name, Player.PEDDERSEN));
    }
//    deal 3 cards to both players
    for(int i = 0; i < 3; i++){
      drawCard(Player.FINDUS);
      drawCard(Player.PEDDERSEN);
    }

  }


  @Override
  public Player getPlayerInTurn() {
    return playerInTurn;
  }

  @Override
  public Hero getHero(Player who) {
    return heroes.get(who);
  }

  @Override
  public Player getWinner() {
    return null;
  }

  @Override
  public int getTurnNumber() {
    return turnNumber;
  }

  @Override
  public int getDeckSize(Player who) {
    return deck.get(who).size();
  }

  @Override
  public Card getCardInHand(Player who, int indexInHand) {
    return hand.get(who).get(indexInHand);
  }

  @Override
  public Iterable<? extends Card> getHand(Player who) {
    return hand.get(who);
  }

  @Override
  public int getHandSize(Player who) {
    return hand.get(who).size();
  }

  @Override
  public Card getCardInField(Player who, int indexInField) {
    return null;
  }

  @Override
  public Iterable<? extends Card> getField(Player who) {
    return field.get(who);
  }

  @Override
  public int getFieldSize(Player who) {
    return field.size();
  }

  @Override
  public void endTurn() {
    if (getPlayerInTurn() ==  Player.FINDUS) {
      playerInTurn = Player.PEDDERSEN;
    } else {
      playerInTurn = Player.FINDUS;
    }
    turnNumber++;
  }

  @Override
  public Status playCard(Player who, Card card, int atIndex) {
    if(getPlayerInTurn() != who){
      return Status.NOT_PLAYER_IN_TURN;
    }else if(card.getOwner() != who){
      return Status.NOT_OWNER;
    }else if(card.getManaCost() > heroes.get(who).getMana()){
      return Status.NOT_ENOUGH_MANA;
    }else {
      hand.get(who).remove(card);
      field.get(who).add(atIndex, (StdCard) card);
      StdHero thisHero = (StdHero) getHero(who);
      thisHero.setMana(thisHero.getMana() - card.getManaCost());
      return Status.OK;
    }
  }

  @Override
  public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
    return null;
  }

  @Override
  public Status attackHero(Player playerAttacking, Card attackingCard) {
    return null;
  }

  @Override
  public Status usePower(Player who) {
    return null;
  }

//  draw a card from the top of the deck
  private void drawCard(Player who){
    StdCard drawnCard =  deck.get(who).getFirst();
    deck.get(who).removeFirst();
    hand.get(who).addFirst(drawnCard);
  }
}
