package chess.game;

import chess.game.pieces.Bishop;
import chess.game.pieces.Empty;
import chess.game.pieces.King;
import chess.game.pieces.Knight;
import chess.game.pieces.Pawn;
import chess.game.pieces.Piece;
import chess.game.pieces.PieceUtils;
import chess.game.pieces.PieceType;
import chess.game.pieces.PlayerColor;
import chess.game.pieces.Queen;
import chess.game.pieces.Rook;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Set;

import static chess.game.pieces.PieceType.*;

public class Board {
    public static final int BOARD_SIZE = 8;
    PieceType[] initialPlacement = {ROOK, KNIGHT, BISHOP, QUEEN, KING, BISHOP, KNIGHT, ROOK};
    public Piece[][] boardState;
    protected final HashMap<String, BoardPosition> boardPositions;

    public BoardPosition whiteKingPosition;
    public BoardPosition blackKingPosition;
    public PlayerColor player1;

    @VisibleForTesting
    public Board() {
        this(PlayerColor.WHITE);
    }

    public Board(PlayerColor player1) {
        boardState = new Piece[BOARD_SIZE][BOARD_SIZE];
        boardPositions = new HashMap<>();
        for (int i=0; i<BOARD_SIZE; i++) {
            for (int j=0; j<BOARD_SIZE; j++) {
                boardState[i][j] = new Empty();
                String file = String.valueOf((char)('A' + j));
                int rank = i + 1;
                String bpKey = PieceUtils.boardPositionKey(rank, file);
                boardPositions.put(bpKey, new BoardPosition(file, rank));
            }
        }
        this.player1 = player1;
    }

    public Board(Board board) {
        this.boardState = board.boardState;
        this.whiteKingPosition = board.whiteKingPosition;
        this.blackKingPosition = board.blackKingPosition;
        boardPositions = board.boardPositions;
    }

    public Piece pieceAt(BoardPosition boardPosition) {
        int rank = BOARD_SIZE - boardPosition.rankOffset - 1;
        return boardState[rank][boardPosition.fileOffset];
    }

    public BoardPosition getBoardPosition(String boardPositionKey) {
        return boardPositions.get(boardPositionKey);
    }

    public void placePiece(Piece piece, BoardPosition bp) {
        int rank = BOARD_SIZE - bp.rankOffset - 1;

        boardState[rank][bp.fileOffset] = piece;
        if (piece.getPieceType() == KING) {
            if (piece.getColor() == PlayerColor.WHITE) {
                whiteKingPosition = bp;
            } else {
                blackKingPosition = bp;
            }
        }
    }

    public void setupBoard() {
        for (int j=0; j<BOARD_SIZE; j++) {
            boardState[0][j] = mapPiece(PlayerColor.BLACK, initialPlacement[j]);
            boardState[1][j] = mapPiece(PlayerColor.BLACK, PAWN);
            boardState[BOARD_SIZE-1][j] = mapPiece(PlayerColor.WHITE, initialPlacement[j]);
            boardState[BOARD_SIZE-2][j] = mapPiece(PlayerColor.WHITE, PAWN);
        }

        whiteKingPosition = getBoardPosition("E1");
        blackKingPosition = getBoardPosition("E8");
    }

    public BoardPosition getKingPosition(PlayerColor playerColor) {
        if (playerColor == PlayerColor.WHITE) {
            return whiteKingPosition;
        } else {
            return blackKingPosition;
        }
    }

    private Piece mapPiece(PlayerColor playerColor, PieceType pieceType) {
        return switch (pieceType) {
            case ROOK -> new Rook(playerColor);
            case KNIGHT -> new Knight(playerColor);
            case BISHOP -> new Bishop(playerColor);
            case QUEEN -> new Queen(playerColor);
            case KING -> new King(playerColor);
            case PAWN -> new Pawn(playerColor);
            case EMPTY -> new Empty();
        };
    }

    public boolean isValidBoardPosition(BoardPosition boardPosition, PlayerColor currPiecePlayerColor) {
        if (boardPosition == null) return false;
        int rank = boardPosition.getRank();
        int file = BoardPosition.convertFileToValue(boardPosition.getFile());

        Piece pieceAtBoardPosition = pieceAt(boardPosition);
        boolean isOnBoard = rank >= 1 && rank <= BOARD_SIZE && file >= 0 && file < BOARD_SIZE;

        return isOnBoard && pieceAtBoardPosition.getColor() != currPiecePlayerColor;
    }

    public static Piece[][] makeDeepCopy(Board board) {
        return java.util.Arrays.stream(board.boardState).map(Piece[]::clone).toArray($ -> board.boardState.clone());
    }

    public boolean isCheck(PlayerColor playerColor) {
        // Returns if playerColor's king is in check.
        PlayerColor opp = PieceUtils.getOppositePlayerColor(playerColor);
        BoardPosition kingPos = getKingPosition(playerColor);

        for (int rank=1; rank<=BOARD_SIZE; rank++) {
            for(int fileOffset=0; fileOffset<BOARD_SIZE; fileOffset++) {
                BoardPosition bp = getBoardPosition(BoardPosition.asBoardKey(rank, fileOffset));
                if (isValidBoardPosition(bp, playerColor) && pieceAt(bp).getColor() == opp) {
                    if (pieceAt(bp).possibleMoves(this, bp).contains(kingPos)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public Set<BoardPosition> squaresUnderAttackByPlayer(PlayerColor playerColor) {
        Set<BoardPosition> possibleMovesByAllPieces = Sets.newHashSet();

        for (int rank=0; rank<BOARD_SIZE; rank++) {
            for(int fileOffset=0; fileOffset<BOARD_SIZE; fileOffset++) {
                BoardPosition bp = getBoardPosition(BoardPosition.asBoardKey(rank, fileOffset));
                if (pieceAt(bp).getColor() == playerColor) {
                    possibleMovesByAllPieces.addAll(pieceAt(bp).possibleMoves(this, bp));
                }
            }
        }

        return possibleMovesByAllPieces;
    }

    public String toString() {
        final String horizontalDivider = "  -- -- -- -- -- -- -- -- \n";

        StringBuilder sb = new StringBuilder();
        sb.append(horizontalDivider);

        for (int i=0; i<BOARD_SIZE; i++) {
            sb.append(String.format("%d|", BOARD_SIZE - i));
            for (int j=0; j<BOARD_SIZE; j++) {
                sb.append(boardState[i][j]);
                sb.append("|");
            }
            sb.append("\n");
            sb.append(horizontalDivider);
        }

        sb.append("   A  B  C  D  E  F  G  H ");

        return sb.toString();
    }
}
