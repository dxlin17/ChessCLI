package pieces;

import chess.game.Board;
import chess.game.BoardPosition;
import chess.game.Game;
import chess.game.Player;
import chess.game.pieces.King;
import chess.game.pieces.PieceUtils;
import chess.game.pieces.PlayerColor;
import chess.game.pieces.Rook;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

public class RookTest {

    @Test
    public void testLegalMoves() {
        Player player = new Player(PlayerColor.WHITE);
        Rook rook = new Rook(player.playerColor);
        King king = new King(player.playerColor);
        Rook enemyRook = new Rook(PieceUtils.getOppositePlayerColor(player.playerColor));

        Board board = new Board();

        BoardPosition boardPosition = board.getBoardPosition("D4");
        board.placePiece(rook, boardPosition);
        board.placePiece(king, board.getBoardPosition("D1"));
        board.placePiece(enemyRook, board.getBoardPosition("D7"));

        Set<BoardPosition> rookMoves = rook.possibleMoves(board, boardPosition);

        Assert.assertEquals(12, rookMoves.size());
        Set<BoardPosition> legalMoves = rookMoves.stream().filter(bp -> Game.isLegalMove(board, boardPosition, bp, PlayerColor.WHITE)).collect(Collectors.toSet());

        Assert.assertEquals(5, legalMoves.size());
    }
}
