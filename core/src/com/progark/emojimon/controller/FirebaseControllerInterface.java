package com.progark.emojimon.controller;

import com.progark.emojimon.model.Player;

import java.util.ArrayList;
import java.util.List;

public interface FirebaseControllerInterface {

    // Returns all places with who's pieces and how many pieces there are


    void addNewGame(String creatorPlayer);

    void addGameDataChangeListener(String gameID);

    void addLastTurnByGameID(String gameID, boolean player, String timeEnd, List<Integer> dices, List<List<Integer>> actions);


}
