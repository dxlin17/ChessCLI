import chess.game.Board;
import chess.game.BoardPosition;
import chess.game.Game;
import chess.game.pieces.King;
import chess.game.pieces.PlayerColor;
import chess.game.pieces.Rook;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class KingTest {
    Board board;

    @Before
    public void setup() {
        board = new Board();
    }

    @Test
    public void testQueenSideCastling() {
        PlayerColor player = PlayerColor.WHITE;
        PlayerColor opp = PlayerColor.BLACK;

        King king = new King(player);
        Rook queensideRook = new Rook(player);
        Rook kingsideRook = new Rook(player);


        board.placePiece(king, board.getBoardPosition("E1"));
        board.placePiece(queensideRook, board.getBoardPosition("H1"));
        board.placePiece(kingsideRook, board.getBoardPosition("A1"));
        Set<BoardPosition> possibleMoves = king.possibleMoves(board, board.getBoardPosition("E1"));
        Assert.assertTrue(
                possibleMoves.containsAll(
                        List.of(board.getBoardPosition("C1"),
                                board.getBoardPosition("G1")
                        )
                )
        );

        Game game = new Game();
        game.board = board;
        game.movePiece(board.getBoardPosition("E1"), board.getBoardPosition("C1"));
        System.out.println(board);
    }

    @Test
    public void testKingSideCastling() {
        PlayerColor player = PlayerColor.WHITE;
        PlayerColor opp = PlayerColor.BLACK;

        King king = new King(player);
        Rook queensideRook = new Rook(player);
        Rook kingsideRook = new Rook(player);


        board.placePiece(king, board.getBoardPosition("E1"));
        board.placePiece(queensideRook, board.getBoardPosition("H1"));
        board.placePiece(kingsideRook, board.getBoardPosition("A1"));
        Set<BoardPosition> possibleMoves = king.possibleMoves(board, board.getBoardPosition("E1"));
        Assert.assertTrue(
                possibleMoves.containsAll(
                        List.of(board.getBoardPosition("C1"),
                                board.getBoardPosition("G1")
                        )
                )
        );

        Game game = new Game();
        game.board = board;
        game.movePiece(board.getBoardPosition("E1"), board.getBoardPosition("G1"));
        System.out.println(board);
    }

    @Test
    public void testCastlingConditions() {
        PlayerColor player = PlayerColor.WHITE;
        PlayerColor opp = PlayerColor.BLACK;

        King king = new King(player);
        Rook queensideRook = new Rook(player);
        Rook kingsideRook = new Rook(player);
        Rook enemyRook = new Rook(opp);
        enemyRook.hasMoved = true;

        board.placePiece(king, board.getBoardPosition("E1"));
        board.placePiece(queensideRook, board.getBoardPosition("H1"));
        board.placePiece(kingsideRook, board.getBoardPosition("A1"));
        board.placePiece(enemyRook, board.getBoardPosition("C8"));
        Set<BoardPosition> possibleMoves = king.possibleMoves(board, board.getBoardPosition("E1"));

        Assert.assertFalse(possibleMoves.contains(board.getBoardPosition("C1")));
    }
}
