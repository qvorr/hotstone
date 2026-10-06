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
import hotstone.framework.Strategy.*;

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
  private final int MAX_MANA = 3;
  private int turnNumber = 0;
  private Map<Player, List<StdCard>> hand = new HashMap<>();
  private Map<Player, StdHero> heroes = new HashMap<>();
  private Map<Player, List<StdCard>> field = new HashMap<>();
  private Map<Player, List<StdCard>> deck = new HashMap<>();
  private ManaStrategy ms;
  private DeckStrategy ds;
  private HeroStrategy hs;
  private WinnerStrategy ws;

  public StandardHotStoneGame(AbstractFactory factory) {
    ms = factory.createManaStrategy();
    ds = factory.createDeckStrategy();
    hs = factory.createHeroStrategy();
    ws = factory.createWinnerStrategy();
    field.put(Player.FINDUS, new ArrayList<>());
    field.put(Player.PEDDERSEN, new ArrayList<>());
    heroes.put(Player.FINDUS, new StdHero(Player.FINDUS, ms, hs));
    heroes.put(Player.PEDDERSEN, new StdHero(Player.PEDDERSEN, ms, hs));
    hand.put(Player.FINDUS, new ArrayList<>());
    hand.put(Player.PEDDERSEN, new ArrayList<>());
    dealCards();

  }

  private void dealCards(){
//    Initialize the decks for both players
    deck.put(Player.FINDUS, new ArrayList<>());
    for(String name : ds.getDeck()){
      deck.get(Player.FINDUS).add(new StdCard(name, Player.FINDUS));
    }
    deck.put(Player.PEDDERSEN, new ArrayList<>());
    for(String name : ds.getDeck()){
      deck.get(Player.PEDDERSEN).add(new StdCard(name, Player.PEDDERSEN));
    }
//    shuffle the cards as needed
    deck.put(Player.FINDUS, ds.arrangeDeck(deck.get(Player.FINDUS)));
    deck.put(Player.PEDDERSEN, ds.arrangeDeck(deck.get(Player.PEDDERSEN)));


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
    Hero currentPlayer =  getHero(getPlayerInTurn());
    Hero opPlayer = getHero(Player.computeOpponent(getPlayerInTurn()));
    return ws.getWinner(currentPlayer, opPlayer, field, turnNumber);
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
    return field.get(who).get(indexInField);
  }

  @Override
  public Iterable<? extends Card> getField(Player who) {
    return field.get(who);
  }

  @Override
  public int getFieldSize(Player who) {
    return field.get(who).size();
  }

  // End turn and helper methods
  @Override
  public void endTurn() {
    turnNumber++;
    playerInTurn = Player.computeOpponent(playerInTurn);

    if (turnNumber > 1){
      drawCardOnNewTurn();
      refillManaOnNewTurn();
      setUsePowerOnHeroOnNewTurn();
      activateCardsOnField();
    }
  }

  private void drawCardOnNewTurn(){
    drawCard(getPlayerInTurn());
  }

  private void refillManaOnNewTurn(){
    StdHero player = (StdHero) getHero(getPlayerInTurn());
    player.refillMana(getTurnNumber()); // turn number if statement not needed here
  }

  private void setUsePowerOnHeroOnNewTurn(){
    StdHero player = (StdHero) getHero(getPlayerInTurn());
    player.setUsePower(true);
  }

  private void activateCardsOnField(){
    for(Card card: getField(getPlayerInTurn())){
      ((StdCard) card).setActive(true);
    }
  }

  // Play card and helper methods
  @Override
  public Status playCard(Player who, Card card, int atIndex) {
    Status status = isPlayCardPossible(who, card);
    if( status != Status.OK ){
      return status;
    }
    executePlayCard(who, card, atIndex);
    return Status.OK;
  }

  public Status isPlayCardPossible(Player who, Card card){
    boolean isPlayersTurn = getPlayerInTurn() == who;
    if(! isPlayersTurn){
      return Status.NOT_PLAYER_IN_TURN;
    }
    boolean isCardOwner = card.getOwner() == who;
    if(! isCardOwner){
      return Status.NOT_OWNER;
    }
    boolean heroHasEnoughMana = card.getManaCost() <= heroes.get(who).getMana();
    if(! heroHasEnoughMana ){
      return Status.NOT_ENOUGH_MANA;
    }

    return Status.OK;
  }

  public void executePlayCard(Player who, Card card, int atIndex){
    hand.get(who).remove(card);
    field.get(who).add(atIndex, (StdCard) card);
    decreaseHeroMana(who, card.getManaCost());
  }

  public void decreaseHeroMana(Player who, int value){
    ((StdHero) getHero(who)).descreaseManaBy(value);
  }

  // Attack card and helper methods
  @Override
  public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
    Status status = isAttackOnCardPossible(playerAttacking, attackingCard, defendingCard);
    if( status != Status.OK ){
      return status;
    }
    executeAttack(attackingCard, defendingCard);
    return Status.OK;
  }

  private void executeAttack(Card attackingCard, Card defendingCard){
    decreaseHealthAndRemoveIfDead(attackingCard, defendingCard);
    decreaseHealthAndRemoveIfDead(defendingCard, attackingCard);

    ((StdCard) attackingCard).setActive(false);
  }

  private void decreaseHealthAndRemoveIfDead(Card attacker, Card defender){
    ((StdCard) attacker).setHealth(defender.getAttack());
    if(attacker.getHealth() <=0){
      field.get(attacker.getOwner()).remove(attacker);
    }
  }

  private Status isAttackOnCardPossible(Player playerAttacking, Card attackingCard, Card defendingCard){
    boolean isPlayerInTurn = getPlayerInTurn() == playerAttacking;
    if(! isPlayerInTurn ) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    boolean isOwnerOfCard = attackingCard.getOwner() == playerAttacking;
    if(! isOwnerOfCard ){
      return Status.NOT_OWNER;
    }
    boolean isCardActive = attackingCard.isActive();
    if(! isCardActive ) {
      return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
    }
    boolean attackingOwnMinion = attackingCard.getOwner() == defendingCard.getOwner();
    if(attackingOwnMinion ){
      return Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION;
    }
    return Status.OK;
  }

  // Attack Hero and helper methods
  @Override
  public Status attackHero(Player playerAttacking, Card attackingCard) {
    if(playerInTurn != playerAttacking){
      return Status.NOT_PLAYER_IN_TURN;
    }else if(attackingCard.getOwner() != playerAttacking){
      return Status.NOT_OWNER;
    }else if(!attackingCard.isActive()){
      return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
    }else{
      StdCard attacker = (StdCard) attackingCard;
      StdHero opHero = (StdHero) getHero(Player.computeOpponent(playerAttacking));
      opHero.setHealth(attackingCard.getAttack());
      attacker.setActive(false);
      return Status.OK;
    }
  }


  // Use Power and helper methods
  @Override
  public Status usePower(Player who) {
    StdHero hero = (StdHero) getHero(who);
    if(playerInTurn != who){
      return Status.NOT_PLAYER_IN_TURN;
    }else if(hero.getMana() < GameConstants.HERO_POWER_COST){
      return Status.NOT_ENOUGH_MANA;
    }else{
      hero.descreaseManaBy(GameConstants.HERO_POWER_COST);
      hero.setUsePower(false);
        Player opponent = Player.computeOpponent(who);
        hs.useHeroPower(getHero(who), getHero(opponent), field.get(who), field.get(opponent));
      return Status.OK;
    }
  }

//  draw a card from the top of the deck
  private void drawCard(Player who){
    if(!deck.get(who).isEmpty()){
      StdCard drawnCard =  deck.get(who).getFirst();
      deck.get(who).removeFirst();
      hand.get(who).addFirst(drawnCard);
    }
  }
}
