package com.progark.emojimon.model.fireBaseData;

import java.util.List;

public class GameData {
    private String status;
    private String player0Key;
    private String player0emoji;
    private String player1Key;
    private String player1emoji;
    private List<List<Integer>> GameBoard;

    public GameData(){} // Reguires for Firebase

    public GameData(String player0) {
        this.status = "Waiting";

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

    public String getPlayer0emoji() {
        return player0emoji;
    }

    public void setPlayer0emoji(String player0emoji) {
        this.player0emoji = player0emoji;
    }

    public String getPlayer1emoji() {
        return player1emoji;
    }

    public void setPlayer1emoji(String player1emoji) {
        this.player1emoji = player1emoji;
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
        return s;
    }
}
