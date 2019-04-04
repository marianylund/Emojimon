package com.progark.emojimon.model.interfaces;

import com.progark.emojimon.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface FirebaseControllerInterface {
    // Add new
    void addNewGame(String creatorPlayer);
    void addGameDataChangeListener(String gameID); // TODO Do we need it here?
    void addLastTurnByGameID(String gameID, boolean player, List<Integer> dices, List<List<Integer>> actions);

    // Setters
    void setGameBoardByGameID(String gameID, List<List<Integer>> gameBoard);
    void setGameStatusByGameID(String gameID, String newStatus);

    void joinGame();
    Object[] getGameIDs();
    void addSubscriber(SubscriberToFirebase subscriber);

    ArrayList getGameStateByGameID(String id);


}
