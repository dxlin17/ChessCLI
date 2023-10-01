import chess.game.Board;
import chess.game.BoardPosition;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class BoardTests {

    @Test
    public void testBoardPosition() {
        BoardPosition bp = new BoardPosition("G", 1);
        assertThat(bp.rankOffset, is(0));
        assertThat(bp.fileOffset, is(6));

        bp = new BoardPosition("G", 8);
        assertThat(bp.rankOffset, is(7));
        assertThat(bp.fileOffset, is(6));
    }

    @Test
    public void testGetBoardPosition() {
        Board board = new Board();
        BoardPosition bp = board.getBoardPosition("G8");

        Assert.assertEquals(8, bp.getRank());
        Assert.assertEquals("G", bp.getFile());
        System.out.println(board);
    }
}
