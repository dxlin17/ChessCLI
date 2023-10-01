package chess.game.pieces;


import chess.game.Board;
import chess.game.BoardPosition;

import java.util.Set;

public class Bishop extends Piece {
    public Bishop(PlayerColor playerColor) {
        super("B", playerColor, PieceType.BISHOP);
    }

    @Override
    public Set<BoardPosition> possibleMoves(Board board, BoardPosition boardPosition) {
        return PieceUtils.possibleDiagonalMoves(board, boardPosition);
    }
}
