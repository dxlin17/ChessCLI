package chess.game.pieces;

public enum PlayerColor {
    WHITE("W"),
    BLACK("B"),
    EMPTY(" ");

    private final String rep;

    PlayerColor(String rep) {
        this.rep = rep;
    }

    public String toString() {
        return rep;
    }
}