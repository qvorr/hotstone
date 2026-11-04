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

package hotstone.figuretestcase;

import hotstone.view.figure.CardFigure;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class TestWordBreak {
  @Test
  public void shouldBreakSentencesIntoNSizeLines() {
    //            01234567890123456789012345678901234567890123456789
    String desc= "Deal damage equal to your hero's attack to a minion";
    List<String> wrapText = CardFigure.breakTextOnWordBoundaryOnLimit(desc, 15);
    wrapText.stream().forEach(System.out::println);
    assertThat(wrapText.get(0), is("Deal damage"));
    assertThat(wrapText.get(1), is("equal to your"));
    assertThat(wrapText.get(2), is("hero's attack"));
    assertThat(wrapText.get(3), is("to a minion"));
  }
}
