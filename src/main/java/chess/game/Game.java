package chess.game;

import chess.game.pieces.*;
import com.google.common.annotations.VisibleForTesting;

import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;

import static chess.game.Board.BOARD_SIZE;


public class Game {
    public Board board;
    public Player turn;
    public Player white;
    public Player black;
    public Player player1;

    // TODO create additional constructor for player to specify color
    public Game() {
        this.black = new Player(PlayerColor.BLACK);
        this.white = new Player(PlayerColor.WHITE);
        this.turn = white;
        player1 = this.white;
        this.board = new Board();
        board.setupBoard();
    }

    public Game(String playerColor) {
        this.board = new Board();
        board.setupBoard();
        this.black = new Player(PlayerColor.BLACK);
        this.white = new Player(PlayerColor.WHITE);
        this.turn = white;
        switch (playerColor) {
            case "w" -> player1 = this.white;
            case "b" -> player1 = this.black;
            default -> {
                System.out.println("Invalid option entered. Playing as White");
                player1 = this.white;
            }
        }
    }

    @VisibleForTesting
    public Game(Board board, PlayerColor turn) {
        this.board = board;
        this.black = new Player(PlayerColor.BLACK);
        this.white = new Player(PlayerColor.WHITE);
        if (turn == PlayerColor.WHITE) {
            this.turn = white;
        } else {
            this.turn = black;
        }
    }

    @VisibleForTesting
    public void setTurn(PlayerColor playerColor) {
        if (playerColor == PlayerColor.WHITE) {
            turn = white;
        } else {
            turn = black;
        }
    }

    public int getPieceScore(Piece piece) {
        switch(piece.getPieceType()) {
            case PAWN -> { return 1; }
            case ROOK -> { return 5; }
            case KNIGHT, BISHOP -> { return 3; }
            case QUEEN -> { return 9; }
            default -> { return 0; }
        }
    }

    public void updatePlayerScore(Piece piece) {
        int pieceValue = getPieceScore(piece);
        this.turn.score += pieceValue;
    }

    public boolean isCastle(BoardPosition start, BoardPosition end, Board board) {
        Piece piece = board.pieceAt(start);
        if (piece.getPieceType() == PieceType.KING) {
            String queensideBp = ((King) piece).queensideCastle[0];
            String kingsideBp = ((King) piece).kingsideCastle[0];
            return (end.toString().equals(queensideBp) || end.toString().equals(kingsideBp));
        }

        return false;
    }

    public void movePiece(BoardPosition start, BoardPosition end) {
        // this should only be called after isLegalMove returns true or call isLegalMove here.
        Piece piece = board.pieceAt(start);
        Piece endPiece = board.pieceAt(end);

        if (!piece.possibleMoves(board, start).contains(end)) return;

        for (BoardPosition bp : board.boardPositions.values()) {
            Piece p = board.pieceAt(bp);
            if (p.getPlayerColor() == turn.playerColor && p.getPieceType() == PieceType.PAWN) {
                // All pawns are no longer eligible for en passant
                Pawn pawn = (Pawn) p;
                pawn.eligibleForEnPassant = false;
            }
        }

        if (Game.isLegalMove(board, start, end, turn.playerColor)) {
            if (piece.getPieceType() == PieceType.KING && isCastle(start, end, board)) {
                King king = (King) piece;
                if (end.toString().equals(king.queensideCastle[0])) {
                    Piece queenRook = board.pieceAt(board.getBoardPosition(king.queensideRook));
                    BoardPosition rookQ = board.getBoardPosition(king.queensideCastle[1]);
                    board.placePiece(queenRook, rookQ);
                    board.placePiece(new Empty(), board.getBoardPosition(king.queensideRook));
                } else {
                    Piece kingRook = board.pieceAt(board.getBoardPosition(king.kingsideRook));
                    BoardPosition rookQ = board.getBoardPosition(king.kingsideCastle[1]);
                    board.placePiece(kingRook, rookQ);
                    board.placePiece(new Empty(), board.getBoardPosition(king.kingsideRook));
                }
            }

            if (piece.getPieceType() == PieceType.PAWN) {
                // If the piece moved was a pawn, 2 spaces. Technically the "hasMoved" check is redundant, but doesn't hurt
                Pawn pawn = (Pawn) piece;
                pawn.eligibleForEnPassant = BoardPosition.fileDistance(start, end) == 2 && !piece.hasMoved;
            }

            board.placePiece(piece, end);
            board.placePiece(new Empty(), start);
            updatePlayerScore(endPiece);
            if (this.turn.playerColor == PlayerColor.WHITE) {
                this.turn = black;
            } else {
                this.turn = white;
            }

            piece.hasMoved = true;
        } else {
            System.out.println("Not a legal move! Try again.");
        }
    }

    /**
     *
     * @param board Board object
     * @param start starting BoardPosition of the piece
     * @param end ending BoardPosition of the piece
     * @param playerColor color of the player making the move
     * @return boolean representing whether it's a legal move for playerColor.
     */
    public static boolean isLegalMove(Board board, BoardPosition start, BoardPosition end, PlayerColor playerColor) {
        Piece piece = board.pieceAt(start);
        if (piece.getPlayerColor() != playerColor || !isValidBoardPosition(board, end, playerColor)) return false;

        // not legal to put own king in check
        Board boardCopy = new Board(board);
        boardCopy.boardState = Board.makeDeepCopy(board);
        boardCopy.placePiece(piece, end);
        boardCopy.placePiece(new Empty(), start);
        if (boardCopy.isCheck(playerColor)) {
            return false;
        }

        return piece.getPieceType() != PieceType.EMPTY;
    }

    public static boolean isValidBoardPosition(Board board, BoardPosition end, PlayerColor playerColor) {
        Piece endLocation = board.pieceAt(end);
        return endLocation.getPlayerColor() != playerColor;
    }

    public static boolean isCheckmate(Board board, PlayerColor playerColor) {
        return board.isCheck(playerColor) && isStalemate(board, playerColor);
    }

    public static boolean isStalemate(Board board, PlayerColor playerColor) {
        for(int rank=0; rank<BOARD_SIZE; rank++) {
            for(int file=0; file<BOARD_SIZE; file++) {
                String key = BoardPosition.asBoardKey(rank, file);
                BoardPosition bp = board.getBoardPosition(key);
                Set<BoardPosition> possibleMoves = board.pieceAt(bp).possibleMoves(board, bp).stream().filter(end -> isLegalMove(board, bp, end, playerColor)).collect(Collectors.toSet());

                possibleMoves.removeIf(move -> !isLegalMove(board, bp, move, playerColor));

                if(board.pieceAt(bp).getPlayerColor() == playerColor && possibleMoves.size() > 0) {
                    System.out.println(board.pieceAt(bp));
                    System.out.println(possibleMoves);
                    return false;
                }
            }
        }

        return true;
    }

    public boolean isGameOver() {
        return isCheckmate(board, turn.playerColor) || isStalemate(board, turn.playerColor);
    }

    public String scoreline() {
        return String.format("Black: %d, White: %d", black.score, white.score);
    }

    public void play() {
        // Game Loop
        Scanner scanner = new Scanner(System.in);
        while(!isGameOver()) {
            System.out.println(board);
            System.out.println(scoreline());
            System.out.print(String.format("Player %s to move. Move: from, to", turn.playerColor));
            if (scanner.hasNextLine()) {
                String move = scanner.nextLine();
                String[] boardPositions = move.split(",");
                String from = boardPositions[0].strip();
                String to = boardPositions[1].strip();

                System.out.println("Move entered: " + from + " " + to);
                BoardPosition start = board.getBoardPosition(from);
                BoardPosition end = board.getBoardPosition(to);

                movePiece(start, end);
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.play();
    }
}
