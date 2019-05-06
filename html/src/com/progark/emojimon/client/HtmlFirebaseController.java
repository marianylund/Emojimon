package com.progark.emojimon.client;

import com.progark.emojimon.model.interfaces.FirebaseControllerInterface;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.interfaces.SubscriberToFirebase;

import java.util.ArrayList;
import java.util.List;

public class HtmlFirebaseController implements FirebaseControllerInterface {
    @Override
    public void addNewGame(String creatorPlayer, List<String> strategies) {

    }

    @Override
    public void addGameDataChangeListener(String gameID) {

    }

    @Override
    public void addLastTurnByGameID(String gameID, boolean player, List<Integer> dices, List<List<Integer>> actions) {

    }

    @Override
    public void setGameBoardByGameID(String gameID, List<List<Integer>> gameBoard) {

    }

    @Override
    public void setGameStatusByGameID(String gameID, String newStatus) {

    }

    @Override
    public void joinGame() {

    }

    @Override
    public Object[] getGameIDs() {
        return new Object[0];
    }

    @Override
    public void addSubscriber(SubscriberToFirebase subscriber) {

    }

    @Override
    public ArrayList getGameStateByGameID(String id) {
        return null;
    }

}
