package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;
import lombok.Getter;

import java.util.Set;

@Getter
public abstract class Piece {
    public boolean hasMoved;
    public final String name;
    public final PlayerColor color;
    public final PieceType pieceType;

    Piece(String name, PlayerColor color, PieceType pieceType) {
        this.name = name;
        this.color = color;
        this.pieceType = pieceType;
    }

    public abstract Set<BoardPosition> possibleMoves(Board board, BoardPosition boardPosition);
    public String toString() {
        return color.toString() + name;
    }
}
