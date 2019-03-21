package com.progark.emojimon.client;

import com.progark.emojimon.controller.FirebaseControllerInterface;
import com.progark.emojimon.model.Player;

import java.util.ArrayList;
import java.util.List;

public class HtmlFirebaseController implements FirebaseControllerInterface {
    @Override
    public List<List<Integer>> getGameStateByGameID(String id) {
        return new ArrayList<List<Integer>>();
    }

    @Override
    public void addNewGame(Player creatorPlayer, List<List<Integer>> gameState) {

    }
}
