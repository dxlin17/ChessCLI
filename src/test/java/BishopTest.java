import chess.game.Board;
import chess.game.BoardPosition;
import chess.game.Game;
import chess.game.Player;
import chess.game.pieces.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

public class BishopTest {

    @Test
    public void testLegalMoves() {
        Player player = new Player(PlayerColor.WHITE);
        Bishop bishop = new Bishop(player.playerColor);
        King king = new King(player.playerColor);
        Rook enemy = new Rook(PieceUtils.getOppositePlayerColor(player.playerColor));

        Board board = new Board();

        BoardPosition boardPosition = board.getBoardPosition("D4");
        board.placePiece(bishop, boardPosition);
        board.placePiece(king, board.getBoardPosition("D1"));
        board.placePiece(enemy, board.getBoardPosition("D7"));

        Set<BoardPosition> bishopMoves = bishop.possibleMoves(board, boardPosition);

        Assert.assertEquals(13, bishopMoves.size());
        Set<BoardPosition> legalMoves = bishopMoves.stream().filter(bp -> Game.isLegalMove(board, boardPosition, bp, PlayerColor.WHITE)).collect(Collectors.toSet());

        Assert.assertEquals(0, legalMoves.size());
    }
}
