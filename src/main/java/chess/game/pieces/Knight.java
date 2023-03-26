package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;
import com.google.common.collect.Sets;

import java.util.Set;

public class Knight extends Piece {
    public PlayerColor playerColor;
    public final String name = "N";
    public final PieceType pieceType = PieceType.KNIGHT;

    public Knight(PlayerColor playerColor) {
        this.playerColor = playerColor;
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
        int rank = boardPosition.getRank() - 1;
        int file = BoardPosition.convertFileToValue(boardPosition.getFile());

        Set<String> bpKeys = Set.of(
                BoardPosition.asBoardKey(rank - 2, file - 1),
                BoardPosition.asBoardKey(rank - 2, file + 1),
                BoardPosition.asBoardKey(rank + 2, file - 1),
                BoardPosition.asBoardKey(rank + 2, file + 1),
                BoardPosition.asBoardKey(rank - 1, file - 2),
                BoardPosition.asBoardKey(rank - 1, file + 2),
                BoardPosition.asBoardKey(rank + 1, file - 2),
                BoardPosition.asBoardKey(rank + 1, file + 2)
        );

        Set<BoardPosition> possibleMoves = Sets.newHashSet();
        for (String key : bpKeys) {
            BoardPosition potentialBoardPosition = board.getBoardPosition(key);
            if (board.isValidBoardPosition(potentialBoardPosition, playerColor)) {
                possibleMoves.add(potentialBoardPosition);
            }
        }

        return possibleMoves;
    }

    public String toString() {
        return name;
    }
}
