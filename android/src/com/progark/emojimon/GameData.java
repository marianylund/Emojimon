package com.progark.emojimon;

import com.progark.emojimon.model.Player;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    protected String status;
    protected Player player0;
    protected Player player1;
    protected List<List<Integer>> GameState;


    public GameData(Player player0, List<List<Integer>> gameState) {
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

    public List<List<Integer>> getGameState() {
        return GameState;
    }

    public void setGameState(List<List<Integer>> gameState) {
        GameState = gameState;
    }
}
