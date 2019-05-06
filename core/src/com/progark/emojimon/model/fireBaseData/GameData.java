package com.progark.emojimon.model.fireBaseData;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class GameData {
    private String status;
    private String player0Key;
    private String player1Key;
    private List<List<Integer>> GameBoard;
    private Settings settings;

    public GameData(){} // Reguires for Firebase

    public GameData(String player0, Settings settings) {
        this.status = "Waiting";
        this.settings = settings;
        // Convert Strings to ENUMS
        // The player who has created the game
        this.player0Key = player0;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPlayer0Key() {
        return player0Key;
    }

    public void setPlayer0Key(String player0Key) {
        this.player0Key = player0Key;
    }

    public String getPlayer1Key() {
        return player1Key;
    }

    public void setPlayer1Key(String player1Key) {
        this.player1Key = player1Key;
    }

    public List<List<Integer>> getGameBoard() {
        return GameBoard;
    }

    public void setGameBoard(List<List<Integer>> gameBoard) {
        this.GameBoard = gameBoard;
    }

    public Settings getSettings() {
        return settings;
    }

    @Override
    public String toString() {
        String s = "";
        if(getStatus() != null){
            s += getStatus() + ",";
        }else{
            s+= "No status ";
        }
        if(getPlayer0Key() != null){
            s += " player0Key is sat";
        } else {
            s += " and no player";
        }
        return settings.toString();
    }
}
