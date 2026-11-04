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

package hotstone.doubles;

import hotstone.framework.*;
import hotstone.observer.GameObserver;
import hotstone.observer.ObserverHandler;
import hotstone.standard.GameConstants;

import java.util.ArrayList;
import java.util.List;

/** A Test Double Fake-Object/Stub implementation of Game. While it could
 * be used for developing the GUI elements for the MiniDraw HotStone
 * UI, the implementation effort to make this fake-object make all
 * relevant state changes amounts to too much work. It is mostly provided
 * here to allow the provided code for the mandatory to execute
 * out-of-the-box.
 */

public class FakeObjectGame implements Game {
  private List<Card> findusHand = new ArrayList<>();
  private Hero findus;

  private List<Card> findusField = new ArrayList<>();
  private List<StubCard> peddersenField = new ArrayList<>();

  private ObserverHandler observerHandler;

  public FakeObjectGame() {
    findusHand.add(0, new StubCard(GameConstants.UNO_CARD, Player.FINDUS, 4));
    findusHand.add(0, new StubCard(GameConstants.DOS_CARD, Player.FINDUS, 3));
    findusHand.add(0, new StubCard(GameConstants.TRES_CARD, Player.FINDUS, 2));

    StubCard pCard = new StubCard("Siete", Player.PEDDERSEN, 7);
    peddersenField.add(0, pCard);

    findus = new StubHero();

    observerHandler = new ObserverHandler();
  }

  @Override
  public Card getCardInHand(Player who, int indexInHand) {
    assert who == Player.FINDUS;
    return findusHand.get(indexInHand);
  }

  @Override
  public Iterable<? extends Card> getHand(Player who) {
    assert who == Player.FINDUS;
    return findusHand;
  }

  @Override
  public int getHandSize(Player who) {
    assert who == Player.FINDUS;
    return findusHand.size();
  }

  @Override
  public Card getCardInField(Player who, int indexInField) {
    if (who == Player.PEDDERSEN) return peddersenField.get(indexInField);
    return findusField.get(indexInField);
  }

  @Override
  public Iterable<? extends Card> getField(Player who) {
    if (who == Player.PEDDERSEN) return peddersenField;
    return findusField;
  }

  @Override
  public int getFieldSize(Player who) {
    if (who == Player.PEDDERSEN) return peddersenField.size();
    return findusField.size();
  }

  @Override
  public Player getPlayerInTurn() {
    return Player.FINDUS;
  }

  @Override
  public Hero getHero(Player who) {
    return findus;
  }

  @Override
  public Player getWinner() {
    return null;
  }

  @Override
  public int getTurnNumber() {
    return 0;
  }

  @Override
  public int getDeckSize(Player who) {
    return 16;
  }

  @Override
  public void endTurn() {
    System.out.println(" FakeObjectGame:: endTurn() called...");
    observerHandler.notifyChangeTurnTo(Player.FINDUS);
  }

  @Override
  public Status playCard(Player who, Card card, int atIndex) {
    System.out.println(" FakeObjectGame:: playCard(" + who + ", " + card.getName() + ") called...");
    if (atIndex != 0) throw new RuntimeException("Ups; I have not yet implemented any index other than handling index=0");
    findusHand.remove(card);
    findusField.add(card);
    observerHandler.notifyPlayCard(who, card, atIndex);
    return Status.OK;
  }

  @Override
  public Status attackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {
    System.out.println(" FakeObjectGame:: attackCard(" + playerAttacking + ", "
            + attackingCard.getName() + ", "
            + defendingCard.getName() +") called...");
    System.out.println(" FakeObjectGame:: REMOVING Findus Attacking Card");
    observerHandler.notifyAttackCard(playerAttacking, attackingCard, defendingCard);

    findusField.remove(attackingCard);
    observerHandler.notifyCardRemove(playerAttacking, attackingCard);

    System.out.println(" FakeObjectGame:: REDUCING health of Defending Card");
    ((StubCard) defendingCard).setHealth(3);
    observerHandler.notifyCardUpdate(defendingCard);
    return Status.OK;
  }

  @Override
  public Status attackHero(Player playerAttacking, Card attackingCard) {
    System.out.println(" FakeObjectGame:: attackHero(" + playerAttacking
            + ", " + attackingCard.getName() + ") called...");
    observerHandler.notifyAttackHero(playerAttacking, attackingCard);
    return Status.OK;
  }

  @Override
  public Status usePower(Player who) {
    System.out.println(" FakeObjectGame:: usePower(" + who
            + ") called...");
    observerHandler.notifyUsePower(who);
    return Status.OK;
  }

  @Override
  public void addObserver(GameObserver observer) {
    observerHandler.addObserver(observer);
  }

}
