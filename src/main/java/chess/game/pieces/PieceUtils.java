package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;

import java.util.*;

import static chess.game.Board.BOARD_SIZE;

public class PieceUtils {

    public static PlayerColor getOppositePlayerColor(PlayerColor playerColor) {
        switch (playerColor) {
            case WHITE -> {
                return PlayerColor.BLACK;
            }
            case BLACK -> {
                return PlayerColor.WHITE;
            }
            case EMPTY -> {
                return PlayerColor.EMPTY;
            }
        }

        return PlayerColor.EMPTY;
    }

    static Set<BoardPosition> possibleStraightMoves(Board board, BoardPosition start) {
        return possibleStraightMovesWithDistance(board, start, BOARD_SIZE);
    }

    static Set<BoardPosition> possibleStraightMovesWithDistance(Board board, BoardPosition start, int maxMoveDistance) {
        Piece piece = board.pieceAt(start);
        PlayerColor opp = getOppositePlayerColor(piece.getPlayerColor());
        int rank = start.getRank();
        String file = start.getFile();
        int fileOffset = BoardPosition.convertFileToValue(file);
        Set<BoardPosition> validMoves = new HashSet<>();

        for (int i=rank+1; i<=rank + maxMoveDistance; i++) {
            BoardPosition possibleBoardPosition = board.getBoardPosition(boardPositionKey(i, file));
            if (board.isValidBoardPosition(possibleBoardPosition, piece.getPlayerColor())) {
                validMoves.add(possibleBoardPosition);
                if (board.pieceAt(possibleBoardPosition).getPlayerColor() == opp) break;
            } else {
                break;
            }
        }

        for (int i=rank-1; i >= rank - maxMoveDistance; i--) {
            BoardPosition possibleBoardPosition = board.getBoardPosition(boardPositionKey(i, file));
            if (board.isValidBoardPosition(possibleBoardPosition, piece.getPlayerColor())) {
                validMoves.add(possibleBoardPosition);

                if (board.pieceAt(possibleBoardPosition).getPlayerColor() == opp) break;
            } else {
                break;
            }
        }

        for (int i=fileOffset+1; i<=fileOffset + maxMoveDistance; i++) {
            BoardPosition possibleBoardPosition = board.getBoardPosition(boardPositionKey(rank, BoardPosition.convertValueToFile(i)));
            if (board.isValidBoardPosition(possibleBoardPosition, piece.getPlayerColor())) {
                validMoves.add(possibleBoardPosition);

                if (board.pieceAt(possibleBoardPosition).getPlayerColor() == opp) break;
            } else {
                break;
            }
        }

        for (int i=fileOffset-1; i >=fileOffset - maxMoveDistance; i--) {
            BoardPosition possibleBoardPosition = board.getBoardPosition(boardPositionKey(rank, BoardPosition.convertValueToFile(i)));
            if (board.isValidBoardPosition(possibleBoardPosition, piece.getPlayerColor())) {
                validMoves.add(possibleBoardPosition);

                if (board.pieceAt(possibleBoardPosition).getPlayerColor() == opp) break;
            } else {
                break;
            }
        }

        return validMoves;
    }

    public static String boardPositionKey(int rank, String file) {
        return String.format("%s%d", file, rank);
    }

    static Set<BoardPosition> possibleDiagonalMoves(Board board, BoardPosition start) {
        return possibleDiagonalMovesWithDistance(board, start, BOARD_SIZE);
    }

    static Set<BoardPosition> possibleDiagonalMovesWithDistance(Board board, BoardPosition start, int maxMoveDistance) {
        Piece piece = board.pieceAt(start);
        PlayerColor opp = getOppositePlayerColor(piece.getPlayerColor());
        int rank = start.getRank();
        String file = start.getFile();
        int fileOffset = BoardPosition.convertFileToValue(file);

        Set<BoardPosition> validMoves = new HashSet<>();

        for (int i=1; i<maxMoveDistance; i++) {
            BoardPosition possibleBoardPosition = board.getBoardPosition(boardPositionKey(rank + i, BoardPosition.convertValueToFile(fileOffset + i)));
            if (board.isValidBoardPosition(possibleBoardPosition, piece.getPlayerColor())) {
                validMoves.add(possibleBoardPosition);

                if (board.pieceAt(possibleBoardPosition).getPlayerColor() == opp) break;
            } else {
                break;
            }
        }

        for (int i=1; i<maxMoveDistance; i++) {
            BoardPosition possibleBoardPosition = board.getBoardPosition(boardPositionKey(rank - i, BoardPosition.convertValueToFile(fileOffset + i)));
            if (board.isValidBoardPosition(possibleBoardPosition, piece.getPlayerColor())) {
                validMoves.add(possibleBoardPosition);

                if (board.pieceAt(possibleBoardPosition).getPlayerColor() == opp) break;
            } else {
                break;
            }
        }
        for (int i=1; i<maxMoveDistance; i++) {
            BoardPosition possibleBoardPosition = board.getBoardPosition(boardPositionKey(rank + i, BoardPosition.convertValueToFile(fileOffset - i)));
            if (board.isValidBoardPosition(possibleBoardPosition, piece.getPlayerColor())) {
                validMoves.add(possibleBoardPosition);

                if (board.pieceAt(possibleBoardPosition).getPlayerColor() == opp) break;
            } else {
                break;
            }
        }
        for (int i=1; i<maxMoveDistance; i++) {
            BoardPosition possibleBoardPosition = board.getBoardPosition(boardPositionKey(rank - i, BoardPosition.convertValueToFile(fileOffset - i)));
            if (board.isValidBoardPosition(possibleBoardPosition, piece.getPlayerColor())) {
                validMoves.add(possibleBoardPosition);

                if (board.pieceAt(possibleBoardPosition).getPlayerColor() == opp) break;
            } else {
                break;
            }
        }

        return validMoves;
    }

    public static boolean isEmptyBetween(Board board, BoardPosition start, BoardPosition end) {
        int rank = start.getRank();
        int file = BoardPosition.convertFileToValue(start.getFile());
        int endFile = BoardPosition.convertFileToValue(end.getFile());

        for (int i=Math.min(file, endFile) + 1; i < Math.max(file, endFile); i++) {
            Piece piece = board.pieceAt(board.getBoardPosition(boardPositionKey(rank, BoardPosition.convertValueToFile(i))));
            if (piece.getPieceType() != PieceType.EMPTY) {
                return false;
            }
        }

        return true;
    }
}
