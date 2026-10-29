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

import hotstone.framework.Card;
import hotstone.framework.Player;

/**
 * TODO: Reflect - is this double a spy or a stub?
 *
 * TODO: Implement this test-double in order to test TDD
 * the implementation of observer events emitted from your
 * Game implementations.
 */
class ObserverDouble implements GameObserver {
  @Override
  public void onPlayCard(Player who, Card card, int atIndex) {}

  @Override
  public void onChangeTurnTo(Player playerBecomingActive) {}

  @Override
  public void onAttackCard(Player playerAttacking, Card attackingCard, Card defendingCard) {}

  @Override
  public void onAttackHero(Player playerAttacking, Card attackingCard) {}

  @Override
  public void onUsePower(Player who) {}

  @Override
  public void onCardDraw(Player who, Card drawnCard) {}

  @Override
  public void onCardUpdate(Card card) {}

  @Override
  public void onCardRemove(Player who, Card card) {}

  @Override
  public void onHeroUpdate(Player who) {}

  @Override
  public void onGameWon(Player playerWinning) {}
}
