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

package hotstone.view.message;

import hotstone.view.GfxConstants;
import minidraw.framework.Drawing;
import minidraw.framework.Figure;
import minidraw.framework.ZOrder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

/**
 * The role of a message system which prints texts in a scroll about events
 * for the game. The scroll automatically expires messages once they have
 * been shown for a given specific number of seconds.
 */
public class MessageSystem {
  private final Drawing drawing;
  private final CountDownTimerThread countDownTimerThread;
  private List<MessageFigure> messageFigureList;

  public MessageSystem(Drawing drawing) {
    this.drawing = drawing;
    messageFigureList = new ArrayList<>();
    // Decorate with a synchronized version
    messageFigureList = Collections.synchronizedList(messageFigureList);
    // Create a timer to remove top element every N milliseconds
    countDownTimerThread = new CountDownTimerThread(GfxConstants.DISPLAY_TIME_MESSAGES_MS);
    new Thread(countDownTimerThread).start();
  }

  public void addText(String text) {
    // Create text and set it at the bottom position
    MessageFigure newMsg = new MessageFigure();
    newMsg.setText(text);
    newMsg.moveBy(GfxConstants.MESSAGE_X, GfxConstants.MESSAGE_Y
            + GfxConstants.MESSAGE_HEIGHT * messageFigureList.size());

    try {
      drawing.writeLock().tryLock(5, TimeUnit.SECONDS);
      try {
        drawing.add(newMsg);
        messageFigureList.add(newMsg);
      } finally {
        drawing.writeLock().unlock();
      }
    } catch (InterruptedException e) {
      // Uhum, we have lost a message, but so be it...
      System.out.println("Warning: Lost message: " + text);
    }

    // 28082025: Shorten the top so it never has more than 8 messages
    if (messageFigureList.size() > GfxConstants.MAX_MESSAGES_TO_DISPLAY) {
      removeTopText();
    }

  }

  private void removeTopText() {
    // Empty and size 1 top texts stays put
    if (messageFigureList.size() <= 1) {
      return;
    }

      // Pop the top text
      Lock wLock = drawing.writeLock();
      try {
        wLock.tryLock(5, TimeUnit.SECONDS);
        Figure figure = messageFigureList.remove(0);
        try {
          drawing.remove(figure);
        } finally {
          drawing.writeLock().unlock();
        }
      } catch (InterruptedException exception) {
        // Uhum, we have lost mesage remove message, but so be it...
        System.out.println("Warning: Lost removal of top message.");
        return;
      }
      // And move the rest up
      messageFigureList.stream()
              .forEach(fig -> {
                        fig.moveBy(0, -GfxConstants.MESSAGE_HEIGHT);
                        drawing.zOrder(fig, ZOrder.TO_TOP);
                      }
              );

  }

  // A count down which waits a set time and then
  // removes the top message
  class CountDownTimerThread implements Runnable {
    private int sleepInMilli;

    public CountDownTimerThread(int displayTimeMessagesMs) {
      sleepInMilli = displayTimeMessagesMs;
    }

    public void run() {
      while (true) {
        try {
          Thread.sleep(sleepInMilli);
        } catch (InterruptedException e) {
        }
        removeTopText();
      }
    }
  }
}