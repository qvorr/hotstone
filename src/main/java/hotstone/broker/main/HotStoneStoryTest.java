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

package hotstone.broker.main;

import frds.broker.Requestor;
import frds.broker.ipc.http.UriTunnelClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotstone.broker.client.CardClientProxy;
import hotstone.broker.client.GameClientProxy;
import hotstone.broker.client.HeroClientProxy;
import hotstone.broker.common.BrokerConstants;
import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.framework.Hero;
import hotstone.framework.Player;

public class HotStoneStoryTest {
  public static void main(String[] args)  {
    // Get the name of the host from the commandline parameters
    String host = args[0];
    // and execute the story test, talking to the server with that name
    new HotStoneStoryTest(host);
  }

  public HotStoneStoryTest(String host) {
    // Create the client side Broker roles
    UriTunnelClientRequestHandler clientRequestHandler
            = new UriTunnelClientRequestHandler(host, BrokerConstants.HOTSTONE_PORT,
            false, BrokerConstants.HOTSTONE_TUNNEL_PATH);
    Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

    Game game = new GameClientProxy(requestor);
    Hero hero = new HeroClientProxy(requestor);
    Card card = new CardClientProxy(requestor);
    testSimpleMethods(game, hero, card);
  }

  private void testSimpleMethods(Game game, Hero hero, Card card) {
    System.out.println("=== Testing pass-by-value methods of Game ===");
    System.out.println(" --> Game turnNumber     " + game.getTurnNumber());
    System.out.println(" --> Game winner         " + game.getWinner());
    System.out.println(" --> Deck size FINDUS    " + game.getDeckSize(Player.FINDUS));
    System.out.println(" --> Deck Size PEDDERSEN " + game.getDeckSize(Player.PEDDERSEN));
    System.out.println(" --> Hand Size PEDDERSEN " + game.getHandSize(Player.FINDUS));
    System.out.println(" --> Hand Size PEDDERSEN " + game.getHandSize(Player.PEDDERSEN));
    System.out.println("=== Testing methods of Hero ===");
    System.out.println(" --> Hero Mana           " + hero.getMana());
    System.out.println(" --> Hero Health         " + hero.getHealth());
    System.out.println(" --> Hero Can Use Power  " + hero.canUsePower());
    System.out.println(" --> Hero Owner          " + hero.getOwner());
    System.out.println("=== Testing methods of Card ===");
    System.out.println(" --> Card Attack         " + card.getAttack());
    System.out.println(" --> Card Health         " + card.getHealth());
    System.out.println(" --> Card Mana Cost      " + card.getManaCost());
    System.out.println(" --> Card Is Active      " + card.isActive());
    System.out.println(" --> Card Owner          " + card.getOwner());
    System.out.println("=== End ===");
  }
}
