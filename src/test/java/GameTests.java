import chess.game.Board;
import chess.game.BoardPosition;
import chess.game.Game;
import chess.game.pieces.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class GameTests {
    Board board;
    BoardPosition start;
    BoardPosition end;

    @Before
    public void setup() {
        board = new Board();
        start = board.getBoardPosition("G8");
        end = board.getBoardPosition("F6");
    }

    @Test
    public void testIsValidBoardPosition() {
        board.placePiece(new Knight(PlayerColor.WHITE), start);
        Game g = new Game(board, PlayerColor.WHITE);
        g.setTurn(PlayerColor.WHITE);

        assertFalse(Game.isValidBoardPosition(g.board, start, PlayerColor.WHITE));
    }

    @Test
    public void testKnightCanMoveToEmptySpace() {
        Game g = new Game();
        g.board.placePiece(new Knight(PlayerColor.WHITE), start);
        assertTrue(Game.isLegalMove(g.board, start, end, PlayerColor.WHITE));
    }

    @Test
    public void testKnightCanTakeOpponentsPiece() {
        board.placePiece(new Knight(PlayerColor.WHITE), start);
        board.placePiece(new Pawn(PlayerColor.BLACK), end);
        Game g = new Game(board, PlayerColor.WHITE);

        assertTrue(Game.isLegalMove(g.board, start, end, PlayerColor.WHITE));
    }

    @Test
    public void testKnightCannotTakeOwnPiece() {
        board.placePiece(new Knight(PlayerColor.WHITE), start);
        board.placePiece(new Pawn(PlayerColor.WHITE), end);
        Game g = new Game(board, PlayerColor.WHITE);

        assertFalse(Game.isLegalMove(g.board, start, end, PlayerColor.WHITE));
    }

    @Test
    public void testStraightPossibleMoves() {
        Game g = new Game();
        BoardPosition rookBp = g.board.getBoardPosition("H1");
        Piece rook = g.board.pieceAt(rookBp);
        assertThat(rook, is(instanceOf(Rook.class)));
        assertThat(rook.getColor(), is(PlayerColor.WHITE));

        Set<BoardPosition> possibleMoves = rook.possibleMoves(g.board, rookBp);
        assertThat(possibleMoves.size(), is(0));

        rook = new Rook(PlayerColor.WHITE);
        board.placePiece(rook, start);
        possibleMoves = rook.possibleMoves(board, start);
        assertThat(possibleMoves.size(), is(14));
    }

    @Test
    public void testDiagonalPossibleMoves() {
        Game g = new Game();
        BoardPosition bishopBp = g.board.getBoardPosition("F1");
        Piece bishop = g.board.pieceAt(bishopBp);
        assertThat(bishop, is(instanceOf(Bishop.class)));
        assertThat(bishop.getColor(), is(PlayerColor.WHITE));

        Set<BoardPosition> possibleMoves = bishop.possibleMoves(g.board, bishopBp);
        assertThat(possibleMoves.size(), is(0));

        bishop = new Bishop(PlayerColor.WHITE);
        board.placePiece(bishop, start);
        assertThat(bishop.possibleMoves(board, start).size(), is(7));
    }

    @Test
    public void testCheck() {
        Board board = new Board();
        King king = new King(PlayerColor.BLACK);
        BoardPosition kingBp = board.getBoardPosition("E1");
        board.placePiece(king, kingBp);

        Rook rook = new Rook(PlayerColor.WHITE);
        BoardPosition rookBp = board.getBoardPosition("E8");
        board.placePiece(rook, rookBp);
        Assert.assertTrue(board.isCheck(PlayerColor.BLACK));
    }

    @Test
    public void testIsLegalMove() {
        Board board = new Board();
        Game g = new Game(board, PlayerColor.BLACK);
        King king = new King(PlayerColor.BLACK);
        BoardPosition kingBp = board.getBoardPosition("E8");
        board.placePiece(king, kingBp);

        Rook rook = new Rook(PlayerColor.BLACK);
        BoardPosition rookBp = board.getBoardPosition("E7");
        board.placePiece(rook, rookBp);

        Rook enemyRook = new Rook(PlayerColor.WHITE);
        BoardPosition enemyRookBp = board.getBoardPosition("E2");
        board.placePiece(enemyRook, enemyRookBp);

        Assert.assertFalse(Game.isLegalMove(g.board, board.getBoardPosition("E7"), board.getBoardPosition("D7"), PlayerColor.BLACK));
    }

    @Test
    public void testIsCheckMate() {
        Queen queen = new Queen(PlayerColor.BLACK);
        Rook rook = new Rook(PlayerColor.BLACK);
        King king = new King(PlayerColor.WHITE);

        Board board = new Board();
        board.placePiece(queen, board.getBoardPosition("A8"));
        board.placePiece(rook, board.getBoardPosition("A7"));
        board.placePiece(king, board.getBoardPosition("H8"));
        Assert.assertTrue(board.isCheck(PlayerColor.WHITE));
        Assert.assertTrue(Game.isCheckmate(board, PlayerColor.WHITE));
    }

    @Test
    public void testIsNotCheckMate() {
        Queen queen = new Queen(PlayerColor.BLACK);
        King king = new King(PlayerColor.WHITE);

        Board board = new Board();
        board.placePiece(queen, board.getBoardPosition("A8"));
        board.placePiece(king, board.getBoardPosition("H8"));
        Assert.assertFalse(Game.isCheckmate(board, PlayerColor.WHITE));
    }
}
