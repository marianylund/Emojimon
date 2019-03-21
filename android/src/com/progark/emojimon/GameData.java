package com.progark.emojimon;

import com.progark.emojimon.model.Player;

public class GameData {
    protected String status;
    protected Player player0;
    protected Player player1;
    protected int[][] GameState;


    public GameData(Player player0, int[][] gameState) {
        this.status = "Waiting";

        // The player who has created the game
        this.player0 = player0;

        // Standard start board ?
        GameState = gameState;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Player getPlayer0() {
        return player0;
    }

    public void setPlayer0(Player player0) {
        this.player0 = player0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public int[][] getGameState() {
        return GameState;
    }

    public void setGameState(int[][] gameState) {
        GameState = gameState;
    }
}
