package chess.game.pieces;

import chess.game.Board;
import chess.game.BoardPosition;
import com.google.common.collect.Sets;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class King extends Piece {
    public boolean hasMoved = false;
    public String queensideRook;
    public String kingsideRook;
    public String[] queensideCastle;
    public String[] kingsideCastle;

    public King(PlayerColor playerColor) {
        super("K", playerColor, PieceType.KING);
        if (playerColor == PlayerColor.WHITE) {
            queensideRook = "A1";
            kingsideRook = "H1";
            queensideCastle = new String[]{"C1", "D1"};
            kingsideCastle = new String[]{"G1", "F1"};
        } else {
            queensideRook = "A8";
            kingsideRook = "H8";
            queensideCastle = new String[]{"C8", "D8"};
            kingsideCastle = new String[]{"G8", "F8"};
        }
    }

    @Override
    public Set<BoardPosition> possibleMoves(Board board, BoardPosition boardPosition) {
        // One square away in any direction + castling. Castling can only be done if king and rook haven't moved yet.
        Set<BoardPosition> straightMoves = PieceUtils.possibleStraightMovesWithDistance(board, boardPosition, 1);
        Set<BoardPosition> diagonalMoves = PieceUtils.possibleDiagonalMovesWithDistance(board, boardPosition, 1);

        // castling
        Set<BoardPosition> castlingMoves = Sets.newHashSet();
        List<String> rooks = List.of(queensideRook, kingsideRook);
        for(String rookBpKey : rooks) {
            BoardPosition rookBp = board.getBoardPosition(rookBpKey);
            if (canCastle(boardPosition, rookBp, board)) {
                if (rookBp.fileOffset < boardPosition.fileOffset) {
                    String boardKey = boardPosition.fileDelta(-2);
                    BoardPosition bp = board.getBoardPosition(boardKey);
                    castlingMoves.add(bp);
                } else {
                    String boardKey = boardPosition.fileDelta(2);
                    BoardPosition bp = board.getBoardPosition(boardKey);
                    castlingMoves.add(bp);
                }
            }
        }

        return Stream.of(straightMoves, diagonalMoves, castlingMoves).flatMap(Collection::stream).collect(Collectors.toSet());
    }

    public boolean canCastle(BoardPosition kingBp, BoardPosition rookBp, Board board) {
        PlayerColor playerColor = getColor();
        PlayerColor opp = PieceUtils.getOppositePlayerColor(playerColor);
        if (!(hasMoved || board.isCheck(playerColor))) {
            Piece piece = board.pieceAt(rookBp);
            if (!piece.hasMoved) {
                Set<BoardPosition> squaresUnderAttackByOpp = board.squaresUnderAttackByPlayer(opp);
                int rank = kingBp.rankOffset;
                int startFile = Math.min(rookBp.fileOffset, kingBp.fileOffset);
                int endFile = Math.max(rookBp.fileOffset, kingBp.fileOffset);

                for (int file=startFile+1; file<endFile; file++) {
                    BoardPosition auditBp = board.getBoardPosition(BoardPosition.asBoardKey(rank, file));
                    if (squaresUnderAttackByOpp.contains(auditBp)) {
                        return false;
                    }
                }

                return true;
            }
        }

        return false;
    }
}
