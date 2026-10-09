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
import hotstone.standard.CardEffects.NoEffect;

import java.util.*;

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

public class StandardHotStoneGame implements Game, MutableGame {
  private Player playerInTurn = Player.FINDUS;  // First player in turn is FINDUS
  private final int MAX_MANA = 3;
  private int turnNumber = 0;
  private Map<Player, List<MutableCard>> hand = new HashMap<>();
  private Map<Player, MutableHero> heroes = new HashMap<>();
  private Map<Player, List<MutableCard>> field = new HashMap<>();
  private Map<Player, List<MutableCard>> deck = new HashMap<>();
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
//        Initialize the decks for both players

    deck.put(Player.FINDUS, new ArrayList<>());
    deck.put(Player.PEDDERSEN, new ArrayList<>());

    for(String name : ds.getDeck()){
      deck.get(Player.FINDUS).add(new StdCard(name, Player.FINDUS, ds, ds.getEffect(name)));
    }

    for(String name : ds.getDeck()){
      deck.get(Player.PEDDERSEN).add(new StdCard(name, Player.PEDDERSEN, ds, ds.getEffect(name)));
    }

//      shuffle the cards as needed
    deck.put(Player.FINDUS, ds.arrangeDeck(deck.get(Player.FINDUS)));
    deck.put(Player.PEDDERSEN, ds.arrangeDeck(deck.get(Player.PEDDERSEN)));


//    deal 3 cards to both players
    for(int i = 0; i < 3; i++){
      drawCard(Player.FINDUS);
      drawCard(Player.PEDDERSEN);
    }
    ds.dealExtraCards(this);

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
    return ws.getWinner(this);
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
  public MutableCard getMutableCardInHand(Player who, int index) {
    return hand.get(who).get(index);
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

  @Override
  public void removeCardOnField(Player who, MutableCard target) {
    field.get(who).remove(target);
  }

  @Override
  public MutableCard getMutableCardInField(Player who, int index){
    return field.get(who).get(index);
  }

  @Override
  public MutableHero getMutableHero(Player who){
    return heroes.get(who);
  }

  @Override
  public void addCardToField(Player who, MutableCard card){
    field.get(who).addLast(card);
  }

  // End turn and helper methods
  @Override
  public void endTurn() {
    turnNumber++;
    playerInTurn = Player.computeOpponent(playerInTurn);

    if (turnNumber > 1) {
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
    if (status != Status.OK) {
      return status;
    }
    executePlayCard(who, card, atIndex);
    return Status.OK;
  }

  public void executePlayCard(Player who, Card card, int atIndex){
//  call to effect strategy for triggering effect
    MutableCard mutableCard = (MutableCard) card;
    mutableCard.triggerEffect(this);
    hand.get(who).remove(card);

//  if a card is a spell, do not play it
    if(mutableCard.getCategory(card.getName()).equals("Spell")){
      return;
    }
    field.get(who).add(atIndex, (StdCard) card);
    decreaseHeroMana(who, card.getManaCost());
  }

  public Status isPlayCardPossible(Player who, Card card){
    boolean isPlayersTurn = getPlayerInTurn() == who;
    if (!isPlayersTurn) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    boolean isCardOwner = card.getOwner() == who;
    if (!isCardOwner){
      return Status.NOT_OWNER;
    }
    boolean heroHasEnoughMana = card.getManaCost() <= heroes.get(who).getMana();
    if (!heroHasEnoughMana) {
      return Status.NOT_ENOUGH_MANA;
    }

    return Status.OK;
  }

  @Override
  public void decreaseHeroMana(Player who, int value){
    getMutableHero(who).decreaseManaBy(value);
  }

  @Override
  public void addCardToHand(Player who, MutableCard card) {
    hand.get(who).addLast(card);
  }

  @Override
  public MutableCard createMutableCard(Player owner, String name, DeckStrategy ds, CardEffectStrategy es) {
    return new StdCard(name, owner, ds, es);
  }

  // Attack card and helper methods
  @Override
  public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
    Status status = isAttackOnCardPossible(playerAttacking, attackingCard, defendingCard);
    if (status != Status.OK) {
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
    ((StdCard) attacker).decreaseHealthBy(defender.getAttack());
    if (attacker.getHealth() <= 0) {
      field.get(attacker.getOwner()).remove(attacker);
    }
  }

  public  Status isAttackOnCardPossible(Player playerAttacking, Card attackingCard, Card defendingCard){
    boolean isPlayerInTurn = getPlayerInTurn() == playerAttacking;
    if (!isPlayerInTurn) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    boolean isOwnerOfCard = attackingCard.getOwner() == playerAttacking;
    if (!isOwnerOfCard) {
      return Status.NOT_OWNER;
    }
    boolean isCardActive = attackingCard.isActive();
    if (!isCardActive) {
      return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
    }

    String defendingCardName = defendingCard.getName();
    boolean hasActiveTauntsOnOpField = hasActiveTauntsOnField(Player.computeOpponent(playerAttacking));
    boolean isDefendingCardTaunt = ((MutableCard)defendingCard).isTaunt(defendingCardName);

    if (hasActiveTauntsOnOpField && !isDefendingCardTaunt) {
      return Status.ATTACK_NOT_ALLOWED_ON_NON_TAUNT_MINION;
    }

    boolean attackingOwnMinion = attackingCard.getOwner() == defendingCard.getOwner();
    if (attackingOwnMinion) {
      return Status.ATTACK_NOT_ALLOWED_ON_OWN_MINION;
    }
    return Status.OK;
  }

  // Attack Hero and helper methods
  @Override
  public Status attackHero(Player playerAttacking, Card attackingCard) {
    Status status = isAttackHeroPossible(playerAttacking, attackingCard);
    if (status != Status.OK) {
      return status;
    }
    executeAttackOnHero(playerAttacking, attackingCard);
    return Status.OK;
  }

  public void executeAttackOnHero(Player playerAttacking, Card attackingCard){
    StdHero opHero = (StdHero) getHero(Player.computeOpponent(playerAttacking));
    opHero.decreaseHealthBy(attackingCard.getAttack());
    deactivateCardAttack(attackingCard);
  }

  public void deactivateCardAttack(Card attacker){
    ((StdCard) attacker).setActive(false);
  }

  public Status isAttackHeroPossible(Player playerAttacking, Card attackingCard) {
    Player opponent = Player.computeOpponent(playerAttacking);

    boolean isPlayerInTurn = playerInTurn == playerAttacking;
    if (!isPlayerInTurn) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    boolean isOwnerOfCard = attackingCard.getOwner() == playerAttacking;
    if (!isOwnerOfCard) {
      return Status.NOT_OWNER;
    }

    boolean hasActiveTauntsOnOpField = hasActiveTauntsOnField(opponent);
    if(hasActiveTauntsOnOpField){
      return Status.ATTACK_NOT_ALLOWED_ON_NON_TAUNT_MINION;
    }

    boolean isCardActive = attackingCard.isActive();
    if (!isCardActive) {
      return Status.ATTACK_NOT_ALLOWED_FOR_NON_ACTIVE_MINION;
    }
    return Status.OK;
  }

  public boolean hasActiveTauntsOnField(Player who){
    for(Card card: getField(who)){
      if(ds.getCategory(card.getName()).getType(card.getName()).equals("Taunt")){
        return true;
      }
    }
    return false;
  }


  // Use Power and helper methods
  @Override
  public Status usePower(Player who) {
    Status status = canUsePower(who, getHero(who));
    if (status != Status.OK) {
      return status;
    }
    executeUseHeroPower(who);
    return Status.OK;
  }

  public void executeUseHeroPower(Player who){
    Player opponent = Player.computeOpponent(who);

//    hs.useHeroPower(getHero(who), getHero(opponent), field.get(who), field.get(opponent));
    hs.useHeroPower(this, who);

    decreaseHeroManaOnPowerUse(getHero(who));
    deactivateHeroPower(who);
  }

  public void deactivateHeroPower(Player who){
    StdHero hero = (StdHero) getHero(who);
    hero.setUsePower(false);
  }

  public void decreaseHeroManaOnPowerUse(Hero hero){
    ((StdHero) hero).decreaseManaBy(GameConstants.HERO_POWER_COST);
  }

  public Status canUsePower(Player who, Hero hero) {
    boolean isPlayerInTurn = playerInTurn == who;
    if(!isPlayerInTurn) {
      return Status.NOT_PLAYER_IN_TURN;
    }
    boolean hasEnoughMana = hero.getMana() >= GameConstants.HERO_POWER_COST;
    if (!hasEnoughMana) {
      return Status.NOT_ENOUGH_MANA;
    }
    return Status.OK;
  }

//  draw a card from the top of the deck
  @Override
  public void drawCard(Player who){
    if (!deck.get(who).isEmpty()) {
      MutableCard drawnCard =  deck.get(who).getFirst();
      deck.get(who).removeFirst();
      hand.get(who).addFirst(drawnCard);
    }
  }

  @Override
  public void addSovsToField() {
    Player pl = Player.PEDDERSEN;
    addCardToField(pl, new StdCard(GameConstants.SOVS_CARD, pl, ds, new NoEffect()));
  }


  @Override
  public void decreaseHeroHealth(Player who, int value) {
    getMutableHero(who).decreaseHealthBy(value);
  }

  @Override
  public void increaseAttackofCardInField(Player who, int index, int value) {
    getMutableCardInField(who, index).increaseAttack(value);
  }

}
