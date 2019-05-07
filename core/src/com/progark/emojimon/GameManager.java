package com.progark.emojimon;

import com.progark.emojimon.controller.FBC;
import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.fireBaseData.Converter;
import com.progark.emojimon.model.fireBaseData.GameData;
import com.progark.emojimon.model.fireBaseData.LastTurnData;
import com.progark.emojimon.model.interfaces.SubscriberToFirebase;

public class GameManager implements SubscriberToFirebase {
    //Implements Singleton pattern with lazy initialization
    private static GameManager INSTANCE;
    private String emoji = "face-with-tears-of-joy_1f602"; // default emoji
    private GameData gameData;
    private LastTurnData lastTurnData;
    private GameBoardController gameBoardController;

    public enum GameStatus {
        WAITING,
        STARTET,
        ENDED
    }

    private boolean currentPlayer = false; // The creator is the first one to go

    // LocalPlayer is defined in AndroidFireBaseController when the player has created or joined the game
    private boolean localPlayer;

    public GameManager(){

    }

    public static GameManager GetInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    public void setGameBoardController(GameBoardController gameBoardController) {
        this.gameBoardController = gameBoardController;
    }

    public GameBoardController getGameBoardController() {
        return gameBoardController;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }

    public void setLastTurnData(LastTurnData lastTurnData) {
        this.lastTurnData = lastTurnData;
    }

    @Override
    public void notifyOfNewLastTurn(String gameID, LastTurnData lastTurn) {
        if (lastTurn != null) {
            lastTurnData = lastTurn;
            currentPlayer = !lastTurnData.getPlayer();
            if(isItLocalPlayerTurn()){
                if(gameBoardController != null) {
                    gameBoardController.showLastTurn(lastTurn);
                    // TODO: GUI: Start turn by enabling roll dice button for player

                }
            }
        }
    }

    @Override
    public void notifyOfGameData(String gameID, GameData gameData) {
        this.gameData = gameData;
        if (gameData.getStatus() == GameStatus.ENDED) {
            //TODO: send to endscreen
        }
    }

    public boolean isItLocalPlayerTurn(){
        return currentPlayer == localPlayer;
    }

    public void setLocalPlayer(boolean localPlayer){
        this.localPlayer = localPlayer;
    }

    public int getLocalPlayer(){
        if(localPlayer){
            return 1;
        }
        return 0;
    }

    public void setGameID(String gameId){
        this.gameData.setGameId(gameId);
    }

    public String getGameID(){
        return gameData.getGameId();
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

    public void endGame() {
        FBC.I().get().endGame(gameData.getGameId());
    }
}
