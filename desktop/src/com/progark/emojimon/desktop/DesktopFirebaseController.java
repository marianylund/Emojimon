package com.progark.emojimon.desktop;

import com.progark.emojimon.model.interfaces.FirebaseControllerInterface;
import com.progark.emojimon.model.Player;

import java.util.ArrayList;
import java.util.List;

public class DesktopFirebaseController implements FirebaseControllerInterface {
    @Override
    public ArrayList<List<Integer>> getGameStateByGameID(String id) { return new ArrayList<List<Integer>>();
    }

    @Override
    public void addNewGame(Player creatorPlayer, List<List<Integer>> gameState) {

    }
}
