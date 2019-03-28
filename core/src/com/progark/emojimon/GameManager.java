package com.progark.emojimon;

public class GameManager {
    //Implements Singleton pattern with lazy initialization
    private static GameManager INSTANCE;

    public GameManager(){

    }

    public static GameManager GetInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

}
