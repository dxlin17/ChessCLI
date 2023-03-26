package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;

import java.util.Set;

public abstract class Piece {
    public boolean hasMoved;
    public abstract PieceType getPieceType();
    public abstract PlayerColor getPlayerColor();
    public abstract Set<BoardPosition> possibleMoves(Board board, BoardPosition boardPosition);
}
