package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;

import java.util.Set;

public class Queen extends Piece {
    public Queen(PlayerColor playerColor) {
        super("Q", playerColor, PieceType.QUEEN);
    }

    @Override
    public Set<BoardPosition> possibleMoves(Board board, BoardPosition start) {
        Set<BoardPosition> straightMoves = PieceUtils.possibleStraightMoves(board, start);
        Set<BoardPosition> diagonalMoves = PieceUtils.possibleDiagonalMoves(board, start);
        straightMoves.addAll(diagonalMoves);
        return straightMoves;
    }
}
