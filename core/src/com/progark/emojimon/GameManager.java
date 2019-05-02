package com.progark.emojimon;

public class GameManager {
    //Implements Singleton pattern with lazy initialization
    private static GameManager INSTANCE;
    private String emoji = "face-with-tears-of-joy_1f602"; // default emoji

    public GameManager(){

    }

    public static GameManager GetInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    public String getEmoji() {
        return emoji;
    }

    public void setEmoji(String emoji) {
        this.emoji = emoji;
    }

}
