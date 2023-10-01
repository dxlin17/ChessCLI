package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;
import com.google.common.collect.Sets;

import java.util.Set;

public class Knight extends Piece {
    public Knight(PlayerColor playerColor) {
        super("N", playerColor, PieceType.KNIGHT);
    }

    @Override
    public Set<BoardPosition> possibleMoves(Board board, BoardPosition boardPosition) {
        int rank = boardPosition.getRank() - 1;
        int file = BoardPosition.convertFileToValue(boardPosition.getFile());
        PlayerColor playerColor = getColor();

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
}
