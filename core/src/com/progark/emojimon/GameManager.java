package com.progark.emojimon;

import com.progark.emojimon.controller.GameBoardController;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.fireBaseData.GameData;
import com.progark.emojimon.model.fireBaseData.LastTurnData;
import com.progark.emojimon.model.interfaces.SubscriberToFirebase;

public class GameManager implements SubscriberToFirebase {
    //Implements Singleton pattern with lazy initialization
    private static GameManager INSTANCE;
    private String localPlayerEmoji = "face-with-tears-of-joy_1f602"; // default localPlayerEmoji
    private String otherPlayerEmoji = "face-screaming-in-fear_1f631";
    private String gameID;
    private LastTurnData lastTurnData;
    private GameData gameData;
    private GameBoardController gameBoardController;
    private EmojimonPreferences preferences;

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

    public int getLocalPlayerIndex(){
        if(localPlayer){
            return 1;
        }
        return 0;
    }

    public Player getLocalPlayer(){
        if(localPlayer){
            return gameBoardController.getPlayer(1);
        }
        else{
            return gameBoardController.getPlayer(0);
        }
    }



    public void setGameID(String gameID){
        this.gameID = gameID;
    }

    public String getGameID(){
        return gameID;
    }

    public String getLocalPlayerEmoji() {
        return  localPlayerEmoji;
    }

    public void setLocalPlayerEmoji(String localPlayerEmoji) {
        this.localPlayerEmoji = localPlayerEmoji;
    }

    public String getOtherPlayerEmoji(){
        return otherPlayerEmoji;
    }

    public void setOtherPlayerEmoji(String otherPlayerEmoji){
        this.otherPlayerEmoji = otherPlayerEmoji;
    }

    public void createPreference(){
        preferences = new EmojimonPreferences();
    }

    public EmojimonPreferences getPreferences(){
        return preferences;
    }
}
