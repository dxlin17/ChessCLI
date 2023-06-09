package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece {
    public final String name = "P";
    public PlayerColor playerColor;
    public final PieceType pieceType = PieceType.PAWN;
    public boolean eligibleForEnPassant = false;

    public Pawn(PlayerColor playerColor) {
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
        Set<BoardPosition> possibleMoveSet = new HashSet<>();
        int direction = 0;

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

            if (board.isValidBoardPosition(newBoardPosition, currPiece.getPlayerColor())) {
                Piece pieceAtNewBoardPosition = board.pieceAt(newBoardPosition);

                // if there aren't any pieces to take or the pawn is blocked by an opponent's piece
                if (i==0 && pieceAtNewBoardPosition.getPlayerColor() != PlayerColor.EMPTY) {
                    continue;
                }

                if (i != 0) {
                    String boardKey = BoardPosition.asBoardKey(start.getRank() - 1, file + i);
                    System.out.println(boardKey);
                    if (board.pieceAt(board.getBoardPosition(boardKey)).getPieceType() == PieceType.PAWN) {
                        System.out.println("first if");
                        Piece p = board.pieceAt(board.getBoardPosition(boardKey));
                        Pawn pawn = (Pawn) p;
                        if (p.getPlayerColor() == opp && pawn.eligibleForEnPassant) {
                            System.out.println("second if");

                            possibleMoveSet.add(newBoardPosition);
                        }
                    } else if (pieceAtNewBoardPosition.getPlayerColor() != opp) {
                        continue;
                    }
                }

                possibleMoveSet.add(newBoardPosition);
            }
        }

        if(!hasMoved) {
            BoardPosition jumpTwo = board.getBoardPosition(BoardPosition.asBoardKey(rank + direction - 1, file));
            if (board.isValidBoardPosition(jumpTwo, currPiece.getPlayerColor())) {
                possibleMoveSet.add(jumpTwo);
            }
        }

        return possibleMoveSet;
    }
}
