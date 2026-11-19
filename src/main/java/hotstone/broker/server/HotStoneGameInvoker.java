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
import hotstone.doubles.StubCard;
import hotstone.doubles.StubHero;
import hotstone.framework.Card;
import hotstone.framework.Game;
import hotstone.broker.common.OperationNames;
import hotstone.framework.Hero;
import hotstone.framework.Player;
import io.javalin.plugin.bundled.RedirectToLowercasePathPlugin;

/** TODO: Template code for solving the Broker exercises */
public class HotStoneGameInvoker implements Invoker {
  private final Game game;
  private final Hero stubhero;
  private final Card stubcard;
  private final Gson gson;


  public HotStoneGameInvoker(Game servant) {
    game = servant;
    stubhero = new StubHero();
    stubcard = new StubCard("Siete", Player.FINDUS, 5, "Six");
    gson = new Gson();
  }

  @Override
  public String handleRequest(String request) {
    RequestObject requestObject = gson.fromJson(request, RequestObject.class);
    JsonArray array = JsonParser.parseString(requestObject.getPayload()).getAsJsonArray();
    String objectId = requestObject.getObjectId();


    ReplyObject reply;
    try{
      if(requestObject.getOperationName().startsWith(OperationNames.GAME_PREFIX)) {
        reply = switch (requestObject.getOperationName()) {
          case OperationNames.GAME_GET_TURN_NUMBER ->
                  new ReplyObject(200, "" + game.getTurnNumber());
          case OperationNames.GAME_GET_DECK_SIZE ->
                  new ReplyObject(200, "" + game.getDeckSize(gson.fromJson(array.get(0), Player.class)));
          case OperationNames.GAME_GET_PLAYER_IN_TURN ->
                  new ReplyObject(200, "" + game.getPlayerInTurn());
          case OperationNames.GAME_GET_HAND_SIZE ->
                  new ReplyObject(200, "" + game.getHandSize(gson.fromJson(array.get(0), Player.class)));
          case OperationNames.GAME_GET_WINNER ->
                  new ReplyObject(200, "" + game.getWinner());
          default ->
                  new ReplyObject(500, "Something went wrong");
        };

      }else if(requestObject.getOperationName().startsWith(OperationNames.HERO_PREFIX)) {
        Hero hero = lookupHero(objectId);

        reply = switch (requestObject.getOperationName()) {
          case OperationNames.HERO_GET_MANA ->
                  new ReplyObject(200, "" + hero.getMana());
          case OperationNames.HERO_GET_HEALTH ->
                  new ReplyObject(200, "" + hero.getHealth());
          case OperationNames.HERO_GET_TYPE ->
                  new ReplyObject(200, hero.getType());
          case OperationNames.HERO_GET_OWNER ->
                  new ReplyObject(200, "" + hero.getOwner());
          case OperationNames.HERO_GET_EFFECT_DESCRIPTION ->
                  new ReplyObject(200, hero.getEffectDescription());
          case OperationNames.HERO_CAN_USE_POWER ->
                  new ReplyObject(200, "" + hero.canUsePower());
          default ->
                  new ReplyObject(500, "Something went wrong");
        };
      }else if(requestObject.getOperationName().startsWith(OperationNames.CARD_PREFIX)){
        Card card = lookupCard(objectId);
        reply = switch(requestObject.getOperationName()){
          case OperationNames.CARD_GET_NAME ->
                  new ReplyObject(200, card.getName());
          case OperationNames.CARD_GET_MANA_COST ->
                  new ReplyObject(200, "" + card.getManaCost());
          case OperationNames.CARD_GET_ATTACK ->
                  new ReplyObject(200, "" + card.getAttack());
          case OperationNames.CARD_GET_HEALTH ->
                  new ReplyObject(200, "" + card.getHealth());
          case OperationNames.CARD_GET_OWNER ->
                  new ReplyObject(200, "" + card.getOwner());
          case OperationNames.CARD_IS_ACTIVE ->
                  new ReplyObject(200, "" + card.isActive());
          case OperationNames.CARD_GET_EFFECT_DESCRIPTION ->
                  new ReplyObject(200, card.getEffectDescription());
          default ->
                  new ReplyObject(500, "Something went wrong");
        };
      } else{
        reply = new ReplyObject(500, "Something went wrong");
      }

    }catch(Exception e){
      reply = new ReplyObject(500, e.getMessage());
    }



    return gson.toJson(reply);
  }

    private Card lookupCard(String objectId) {
        return stubcard;
    }


  private Hero lookupHero(String objectId){
    return stubhero;
  }
}
