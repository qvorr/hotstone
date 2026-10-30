package hotstone.transcript;

import hotstone.framework.*;
import hotstone.standard.Factories.AlphaFactory;
import hotstone.standard.StandardHotStoneGame;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TestTranscript {

    @Test
    public void shouldPrintTranscriptOfSuccessfulActions() {
        Game realGame = new StandardHotStoneGame(new AlphaFactory());

        // transcript on
        Game game = new TranscriptGame(realGame);

        game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 2), 0);  // printed
        Status failed = game.playCard(Player.FINDUS, game.getCardInHand(Player.FINDUS, 0), 0);  // not printed, too little mana
        assertThat(failed, is(Status.NOT_ENOUGH_MANA));
        game.endTurn();  // printed

        game.usePower(Player.PEDDERSEN);  // printed
        game.endTurn();  // printed

        // transcript off
        game = realGame;
        game.endTurn();  // not printed

        // transcript on again
        game = new TranscriptGame(realGame);
        game.endTurn();  // printed

        assertThat(game.getTurnNumber(), is(4));
    }
}