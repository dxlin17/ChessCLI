package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
    public boolean eligibleForEnPassant = false;

    public Pawn(PlayerColor playerColor) {
        super("P", playerColor, PieceType.PAWN);
    }

    @Override
    public Set<BoardPosition> possibleMoves(Board board, BoardPosition start) {
        Set<BoardPosition> possibleMoveSet = new HashSet<>();
        int direction = 0;
        PlayerColor playerColor = getColor();

        if (playerColor == PlayerColor.WHITE) {
            direction = 1;
        } else if (playerColor == PlayerColor.BLACK){
            direction = -1;
        }

        int rank = start.getRank() + direction;
        int file = BoardPosition.convertFileToValue(start.getFile());
        Piece currPiece = board.pieceAt(start);
        PlayerColor opp = PieceUtils.getOppositePlayerColor(playerColor);

        for (int i=-1; i<=1; i++) {
            String key = BoardPosition.asBoardKey(rank - 1, file + i);
            BoardPosition newBoardPosition = board.getBoardPosition(key);

            if (board.isValidBoardPosition(newBoardPosition, currPiece.getColor())) {
                Piece pieceAtNewBoardPosition = board.pieceAt(newBoardPosition);

                // if there aren't any pieces to take or the pawn is blocked by an opponent's piece
                if (i==0 && pieceAtNewBoardPosition.getColor() != PlayerColor.EMPTY) {
                    continue;
                }

                if (i != 0) {
                    String boardKey = BoardPosition.asBoardKey(start.getRank() - 1, file + i);
                    if (board.pieceAt(board.getBoardPosition(boardKey)).getPieceType() == PieceType.PAWN) {
                        Piece p = board.pieceAt(board.getBoardPosition(boardKey));
                        Pawn pawn = (Pawn) p;
                        if (p.getColor() == opp && pawn.eligibleForEnPassant) {

                            possibleMoveSet.add(newBoardPosition);
                        }
                    } else if (pieceAtNewBoardPosition.getColor() != opp) {
                        continue;
                    }
                }

                possibleMoveSet.add(newBoardPosition);
            }
        }

        if(!hasMoved) {
            BoardPosition jumpTwo = board.getBoardPosition(BoardPosition.asBoardKey(rank + direction - 1, file));
            if (board.isValidBoardPosition(jumpTwo, currPiece.getColor())) {
                possibleMoveSet.add(jumpTwo);
            }
        }

        return possibleMoveSet;
    }
}
