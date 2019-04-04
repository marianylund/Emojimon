package com.progark.emojimon.model.fireBaseData;

import com.progark.emojimon.model.Player;

import java.util.List;

public class LastTurnData {
    protected boolean player;
    protected List<Integer> dices;
    protected List<List<Integer>> actions;

    public LastTurnData(){} // Reguires for Firebase

    public LastTurnData(boolean player, List<Integer> dices, List<List<Integer>> actions) {
        this.player = player;

        this.dices = dices;
        this.actions = actions;
    }

    public boolean getPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    public List<Integer> getDices() {
        return dices;
    }

    public void setDices(List<Integer> dices) {
        this.dices = dices;
    }

    public List<List<Integer>> getActions() {
        return actions;
    }

    public void setActions(List<List<Integer>> actions) {
        this.actions = actions;
    }
}
