package com.progark.emojimon.client;

import com.progark.emojimon.GameManager;
import com.progark.emojimon.model.fireBaseData.GameData;
import com.progark.emojimon.model.fireBaseData.Settings;
import com.progark.emojimon.model.interfaces.FirebaseControllerInterface;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.interfaces.SubscriberToFirebase;

import java.util.ArrayList;
import java.util.List;

public class HtmlFirebaseController implements FirebaseControllerInterface {

    @Override
    public void testWrite(String testMessage) {

    }

    @Override
    public void createNewGame(String creatorPlayer, Settings strategies) {

    }

    @Override
    public void addGameDataChangeListener(String gameID) {

    }

    @Override
    public void updateLastTurn(String gameID, boolean player, List<Integer> dices, List<List<Integer>> actions) {

    }

    @Override
    public void setGameBoardByGameID(String gameID, List<List<Integer>> gameBoard) {

    }

    @Override
    public void setGameStatusByGameID(String gameID, GameManager.GameStatus newStatus) {

    }

    @Override
    public void joinGame() {

    }

    @Override
    public void addSubscriber(SubscriberToFirebase subscriber) {

    }

    @Override
    public void endGame(String gameId, boolean isCreator) {

    }

    @Override
    public void updateGameData(String gameId, GameData gameData) {

    }

    @Override
    public ArrayList getGameStateByGameID(String id) {
        return null;
    }

}
