package com.progark.emojimon.client;

import com.progark.emojimon.controller.FirebaseControllerInterface;

public class FirebaseController implements FirebaseControllerInterface {
    @Override
    public int[][] getGameStateByGameID(String id) {
        return new int[0][];
    }
}
