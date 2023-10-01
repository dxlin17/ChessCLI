package chess.game.pieces;


import chess.game.Board;
import chess.game.BoardPosition;

import java.util.HashSet;
import java.util.Set;

public class Empty extends Piece {
    public Empty() {
        super(" ", PlayerColor.EMPTY, PieceType.EMPTY);
    }

    @Override
    public Set<BoardPosition> possibleMoves(Board board, BoardPosition boardPosition) {
        return new HashSet<>();
    }
}
