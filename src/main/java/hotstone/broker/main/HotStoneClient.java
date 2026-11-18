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
import hotstone.broker.client.GameClientProxy;
import hotstone.broker.common.BrokerConstants;
import hotstone.framework.Game;
import hotstone.framework.Player;
/* TODO: Uncomment these to get the MiniDraw library
import minidraw.framework.DrawingEditor;
import minidraw.framework.Factory;
import minidraw.standard.MiniDrawApplication;
*/

public class HotStoneClient {
  public static void main(String[] args) {
    // Get the name of the host and player from the commandline parameters
    String host = args[0];
    String who = args[1];
    System.out.println("=== Starting Client for player " + who + " on server at " + host + " ===");
    new HotStoneClient(host, who);
  }

  public HotStoneClient(String host, String playerAsString) {
    Player who = playerAsString.equalsIgnoreCase("findus") ? Player.FINDUS : Player.PEDDERSEN;

    // Create the client side Broker roles
    UriTunnelClientRequestHandler clientRequestHandler
            = new UriTunnelClientRequestHandler(host, BrokerConstants.HOTSTONE_PORT,
            false, BrokerConstants.HOTSTONE_TUNNEL_PATH);
    Requestor requestor = new StandardJSONRequestor(clientRequestHandler);

    // Create the game client proxy
    Game game = new GameClientProxy(requestor);

    // Next setup the MiniDraw HotStone UI for that game

    /* TODO: Create a MiniDraw Application with the Factory
     * in OPPONENT_MODE, open the editor, and set a tool that
     * can handle the normal user events PLUS allows hitting the
     * 'Next Opp Act' / refresh button to do 'brute force redrawing'
     */

   /* Code is something ala
    DrawingEditor editor =
            new MiniDrawApplication( "HotStone Client on host: " + host,
                    new HotStoneFactory(game, who,
                            HotStoneDrawingType.OPPONENT_MODE) );
    editor.open();

    editor.setTool(new RemotePlayStateTool(...));
  */
  }
}
