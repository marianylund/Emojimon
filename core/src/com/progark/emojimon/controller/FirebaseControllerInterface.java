package com.progark.emojimon.controller;

import com.progark.emojimon.model.Player;

import java.util.ArrayList;
import java.util.List;

public interface FirebaseControllerInterface {

    // Returns all places with who's pieces and how many pieces there are
    public List<List<Integer>> getGameStateByGameID(String id);

    void addNewGame(Player creatorPlayer, List<List<Integer>> gameState);


}
