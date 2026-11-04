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

package hotstone.view.tool;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestMinionDropIndex {
  private List<Integer> listOfXPositionOfMinions;

  @BeforeEach
  public void setup() {
    // Data from a real minion placement of 5 minions in unsorted order
    listOfXPositionOfMinions = List.of(505, 655, 205, 355, 805);
  }

  @Test
  public void shouldCompute0IndexForLeftDrop() {
    // Given a drop X of 155
    int droppedX = 155;
    // When computing index to put minion onto
    DropZone d;
    int index = DropZone.computeFieldIndexForDropAtX(droppedX, listOfXPositionOfMinions);
    // Then it is index 0 = left of first minion
    assertThat(index, is(0));
  }

  @Test
  public void shouldCompute5IndexForRightDrop() {
    // Given a drop right of rigthmost minion
    int droppedX = 815;
    // When computing index to put minion onto
    DropZone d;
    int index = DropZone.computeFieldIndexForDropAtX(droppedX, listOfXPositionOfMinions);
    // Then it is index 5 = right of minion #5
    assertThat(index, is(5));
  }

  @Test
  public void shouldCompute2IndexForDropBetweenSecondAndThird() {
    // Given a drop X of 155
    int droppedX = 412;
    // When computing index to put minion onto
    DropZone d;
    int index = DropZone.computeFieldIndexForDropAtX(droppedX, listOfXPositionOfMinions);
    // Then it is index 2 = between second and third
    assertThat(index, is(2));
  }

  @Test
  public void shouldCompute0IfEmptyList() {
    // Given an empty list
    listOfXPositionOfMinions = List.of();
    // When computing index to put minion onto
    DropZone d;
    int index = DropZone.computeFieldIndexForDropAtX(100, listOfXPositionOfMinions);
    // Then it is index 0 = left of first minion
    assertThat(index, is(0));
  }

  @Test
  public void shouldIncreaseCoverage() {
    new DropZone();
  }
}
