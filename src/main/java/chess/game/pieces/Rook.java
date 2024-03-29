package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;

import java.util.Set;

public class Rook extends Piece {
    public boolean hasMoved = false;

    public Rook(PlayerColor playerColor) {
        super("R", playerColor, PieceType.ROOK);
    }

    @Override
    public Set<BoardPosition> possibleMoves(Board board, BoardPosition start) {
        return PieceUtils.possibleStraightMoves(board, start);
    }
}
