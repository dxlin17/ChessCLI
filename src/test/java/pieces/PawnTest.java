package pieces;

import chess.game.Board;
import chess.game.BoardPosition;
import chess.game.pieces.Pawn;
import chess.game.pieces.PlayerColor;
import com.beust.jcommander.internal.Sets;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

public class PawnTest {
    @Test
    public void testLegalMoves() {
        Pawn pawn = new Pawn(PlayerColor.WHITE);
        pawn.hasMoved = true;
        Board board = new Board();
        BoardPosition start = board.getBoardPosition("D4");

        board.placePiece(pawn, start);
        Set<BoardPosition> possibleMoves = pawn.possibleMoves(board, start);
        Assert.assertEquals(1, possibleMoves.size());

        Pawn enemyPawn = new Pawn(PlayerColor.BLACK);
        enemyPawn.hasMoved = true;
        BoardPosition enemyBoardPosition = board.getBoardPosition("C5");
        board.placePiece(enemyPawn, enemyBoardPosition);
        possibleMoves = pawn.possibleMoves(board, start);
        Set<BoardPosition> enemyPossibleMoves = enemyPawn.possibleMoves(board, enemyBoardPosition);
        Assert.assertEquals(2, possibleMoves.size());
        Assert.assertEquals(2, enemyPossibleMoves.size());

        Set<BoardPosition> expectedLegalMoves = Sets.newHashSet();
        expectedLegalMoves.add(board.getBoardPosition("D5"));
        expectedLegalMoves.add(enemyBoardPosition);
        Assert.assertEquals(expectedLegalMoves, possibleMoves);
    }

    @Test
    public void testEnPassant() {
        Pawn pawn = new Pawn(PlayerColor.WHITE);
        pawn.hasMoved = true;
        Board board = new Board();
        BoardPosition start = board.getBoardPosition("D5");
        board.placePiece(pawn, start);

        Pawn enemyPawn = new Pawn(PlayerColor.BLACK);
        enemyPawn.hasMoved = true;
        BoardPosition enemyBoardPosition = board.getBoardPosition("E5");
        board.placePiece(enemyPawn, enemyBoardPosition);
        enemyPawn.eligibleForEnPassant = true;

        Assert.assertTrue(pawn.possibleMoves(board, start).contains(board.getBoardPosition("E6")));
    }
}
