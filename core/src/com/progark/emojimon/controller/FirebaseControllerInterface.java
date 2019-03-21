package com.progark.emojimon.controller;

public interface FirebaseControllerInterface {

    // Returns all places with who's pieces and how many pieces there are
    public int[][] getGameStateByGameID(String id);


}
