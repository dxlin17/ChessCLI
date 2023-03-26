package chess.game;

import chess.game.pieces.PlayerColor;

public class Player {
    public PlayerColor playerColor;
    int direction;
    int score;

    public Player (PlayerColor playerColor) {
        this.playerColor = playerColor;
        if (playerColor == PlayerColor.WHITE) direction = -1; else direction = 1;
        this.score = 0;
    }
}
