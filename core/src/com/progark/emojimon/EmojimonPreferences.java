package com.progark.emojimon;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class EmojimonPreferences {

    private String PREF_EMOJI = "";
    private String PREF_GAMEID = "gameid";
    private Preferences preferences;

    public EmojimonPreferences(){
        this.preferences = Gdx.app.getPreferences("my_emojimon_preferences");
    }


    public String isEmoji() {
        return preferences.getString(PREF_EMOJI);
    }
    public void setEmoji(String emoji) {
        preferences.putString(PREF_EMOJI, emoji);
        preferences.flush();
        System.out.print(PREF_EMOJI);
    }

    public int isGameID() {
        return preferences.getInteger(PREF_GAMEID, 0);
    }

    public void setGameID(int gameID) {
        preferences.putInteger(PREF_GAMEID, gameID);
        preferences.flush();
    }

}
