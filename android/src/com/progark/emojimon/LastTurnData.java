package com.progark.emojimon;

import com.progark.emojimon.model.Player;

public class LastTurnData {
    protected Player player;
    protected String timeEnd;
    protected int[] dices;
    protected int[][] actions;


    public LastTurnData(Player player, String timeEnd, int[] dices, int[][] actions) {
        this.player = player;
        this.timeEnd = timeEnd;
        this.dices = dices;
        this.actions = actions;
    }
}
