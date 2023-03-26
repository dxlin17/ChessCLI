package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;

import java.util.Set;

public class Rook extends Piece {
    public PlayerColor playerColor;
    public final String name = "R";
    public final PieceType pieceType = PieceType.ROOK;
    public boolean hasMoved = false;


    public Rook(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

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
    public Set<BoardPosition> possibleMoves(Board board, BoardPosition start) {
        return PieceUtils.possibleStraightMoves(board, start);
    }
}
