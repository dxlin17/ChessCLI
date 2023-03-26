package chess.game.pieces;


import chess.game.Board;
import chess.game.BoardPosition;

import java.util.HashSet;
import java.util.Set;

public class Empty extends Piece {
    public final String name = " ";
    public final PieceType pieceType = PieceType.EMPTY;
    public final PlayerColor playerColor = PlayerColor.EMPTY;

    public String toString() {
        return name;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    @Override
    public Set<BoardPosition> possibleMoves(Board board, BoardPosition boardPosition) {
        return new HashSet<>();
    }
}
