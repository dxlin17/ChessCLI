import chess.game.Board;
import chess.game.BoardPosition;
import chess.game.pieces.Knight;
import chess.game.pieces.PlayerColor;
import org.junit.Assert;
import org.junit.Test;

public class KnightTest {

    @Test
    public void testLegalMoves() {
        Board board = new Board();
        Knight knight = new Knight(PlayerColor.WHITE);
        BoardPosition bp = board.getBoardPosition("D4");

        Assert.assertEquals(8, knight.possibleMoves(board, bp).size());
    }
}
