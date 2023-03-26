package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;

import java.util.Set;

public class Queen extends Piece {
    public PlayerColor playerColor;
    public final String name = "Q";
    public final PieceType pieceType = PieceType.QUEEN;

    public Queen(PlayerColor playerColor) {
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
        Set<BoardPosition> straightMoves = PieceUtils.possibleStraightMoves(board, start);
        Set<BoardPosition> diagonalMoves = PieceUtils.possibleDiagonalMoves(board, start);
        straightMoves.addAll(diagonalMoves);
        return straightMoves;
    }
}
