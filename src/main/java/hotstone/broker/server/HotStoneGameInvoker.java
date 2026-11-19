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

package hotstone.broker.server;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotstone.framework.Game;
import hotstone.broker.common.OperationNames;
import hotstone.framework.Player;

/** TODO: Template code for solving the Broker exercises */
public class HotStoneGameInvoker implements Invoker {
  private final Game game;
  private final Gson gson;


  public HotStoneGameInvoker(Game servant) {
    game = servant;
    gson = new Gson();
  }

  @Override
  public String handleRequest(String request) {
    RequestObject requestObject = gson.fromJson(request, RequestObject.class);
    JsonArray array = JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();


    ReplyObject reply;
    try{
      reply = switch(requestObject.getOperationName()){
        case OperationNames.GAME_GET_TURN_NUMBER ->
                new ReplyObject(200, "" + game.getTurnNumber()) ;
        case OperationNames.GAME_GET_PLAYER_IN_TURN ->
                new ReplyObject(200, "" + game.getPlayerInTurn());
        case OperationNames.GAME_GET_DECK_SIZE ->
                new ReplyObject(200, "" + game.getDeckSize(gson.fromJson(array.get(0), Player.class)));
        case OperationNames.GAME_GET_HAND_SIZE ->
                new ReplyObject(200, "" + game.getHandSize(gson.fromJson(array.get(0), Player.class)));
        default ->
                new ReplyObject(500, "Something went wrong");
      };
    }catch(Exception e){
      reply = new ReplyObject(500, e.getMessage());
    }



    return gson.toJson(reply);
  }
}
