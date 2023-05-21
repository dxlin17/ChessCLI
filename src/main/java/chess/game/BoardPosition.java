package chess.game;

import chess.game.pieces.PieceUtils;

public class BoardPosition {
    public int rankOffset;
    // Offset from file 'A'
    public int fileOffset;
    public BoardPosition(String file, int rank) {
        this.fileOffset = file.toLowerCase().charAt(0) - 'a';
        this.rankOffset = rank - 1;
    }

    public BoardPosition(int rankOffset, int fileOffset) {
        this.rankOffset = rankOffset;
        this.fileOffset = fileOffset;
    }

    public int getRank() {
        return rankOffset + 1;
    }

    public String getFile() {
        return String.valueOf((char)('A' + fileOffset));
    }

    public static int convertFileToValue(String file) {
        return file.toLowerCase().charAt(0) - 'a';
    }

    public static String convertValueToFile(int offset) {
        return String.valueOf((char)('A' + offset));
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BoardPosition)) return false;
        return ((BoardPosition) other).rankOffset == this.rankOffset && ((BoardPosition) other).fileOffset == this.fileOffset;
    }

    public String fileDelta(int fileOffset) {
        return asBoardKey(this.rankOffset, this.fileOffset + fileOffset);
    }

    public String toString() {
        return PieceUtils.boardPositionKey(rankOffset + 1, String.valueOf((char)('A' + fileOffset)));
    }

    public static String asBoardKey(int rank, int file) {
        return String.format("%s%d", convertValueToFile(file), rank + 1);
    }

    public static int fileDistance(BoardPosition a, BoardPosition b) {
        return Math.abs(a.fileOffset - b.fileOffset);
    }
}