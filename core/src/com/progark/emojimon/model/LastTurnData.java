package com.progark.emojimon.model;

import com.progark.emojimon.model.Player;

import java.util.List;

public class LastTurnData {
    protected boolean player;
    protected String timeEnd;
    protected List<Integer> dices;
    protected List<List<Integer>> actions;

    public LastTurnData(){} // Reguires for Firebase

    public LastTurnData(boolean player, String timeEnd, List<Integer> dices, List<List<Integer>> actions) {
        this.player = player;
        this.timeEnd = timeEnd;
        this.dices = dices;
        this.actions = actions;
    }

    public boolean getPlayer() {
        return player;
    }

    public void setPlayer(boolean player) {
        this.player = player;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
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
