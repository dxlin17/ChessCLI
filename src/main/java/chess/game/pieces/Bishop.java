package chess.game.pieces;


import chess.game.Board;
import chess.game.BoardPosition;

import java.util.Set;

public class Bishop extends Piece {
    public final String name = "B";
    public PlayerColor playerColor;
    public final PieceType pieceType = PieceType.BISHOP;

    public Bishop(PlayerColor playerColor) {
        this.playerColor = playerColor;
    }

    public String toString() {
        return name;
    }

    @Override
    public PlayerColor getPlayerColor() {
        return playerColor;
    }

    @Override
    public Set<BoardPosition> possibleMoves(Board board, BoardPosition boardPosition) {
        return PieceUtils.possibleDiagonalMoves(board, boardPosition);
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }
}
