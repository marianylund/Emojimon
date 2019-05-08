package com.progark.emojimon.desktop;

import com.progark.emojimon.model.fireBaseData.Settings;
import com.progark.emojimon.model.interfaces.FirebaseControllerInterface;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.interfaces.SubscriberToFirebase;

import java.util.ArrayList;
import java.util.List;

public class DesktopFirebaseController implements FirebaseControllerInterface {

    @Override
    public ArrayList<List<Integer>> getGameStateByGameID(String id) { return new ArrayList<List<Integer>>();
    }


    @Override
    public void testWrite(String testMessage) {

    }

    @Override
    public void addNewGame(String creatorPlayer, Settings strategies) {

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
}
