import chess.game.Board;

public class Main {
    public static void main(String[] argS) {
        Board b = new Board();
        b.setupBoard();
        System.out.println(b);
    }
}
