package com.progark.emojimon.model.fireBaseData;

public class Settings {
    String canClearStrat;
    String moveSetStrat;
    String moveValStrat;
    String numOfDice;

    public Settings(){};

    public Settings(String canClearStrat, String moveSetStrat, String moveValStrat, String numOfDice) {
        this.canClearStrat = canClearStrat;
        this.moveSetStrat = moveSetStrat;
        this.moveValStrat = moveValStrat;
        this.numOfDice = numOfDice;
    }

    public String getCanClearStrat() {
        return canClearStrat;
    }

    public String getMoveSetStrat() {
        return moveSetStrat;
    }

    public String getMoveValStrat() {
        return moveValStrat;
    }

    public String getNumOfDice() {
        return numOfDice;
    }
}
