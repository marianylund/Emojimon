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

    private String gameID;
    private LastTurnData lastTurnData;
    private GameData gameData;
    private GameBoardController gameBoardController;

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

    @Override
    public void notifyOfNewLastTurn(String gameID, LastTurnData lastTurn) {
        lastTurnData = lastTurn;
        currentPlayer = !lastTurnData.getPlayer();
        if(isItLocalPlayerTurn()){
            if(gameBoardController != null) {
                gameBoardController.showLastTurn(lastTurn);
                // TODO: GUI: Start turn by enabling roll dice button for player

            }
        }
    }

    @Override
    public void notifyOfGameData(String gameID, GameData gameData) {
        this.gameData = gameData;
    }

    public boolean isItLocalPlayerTurn(){
        return currentPlayer == localPlayer;
    }

    public void setLocalPlayer(boolean localPlayer){
        this.localPlayer = localPlayer;
    }

    public void setGameID(String gameID){
        this.gameID = gameID;
    }
}
