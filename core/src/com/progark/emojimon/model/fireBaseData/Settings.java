package com.progark.emojimon.model.fireBaseData;

import com.progark.emojimon.model.factories.MoveValidationStrategyFactory.MoveValStrat;
import com.progark.emojimon.model.factories.MoveSetStrategyFactory.MoveSetStrat;
import com.progark.emojimon.model.factories.CanClearStrategyFactory.CanClearStrat;

public class Settings {
    int boardSize;
    int pieces;
    int baseNumberOfDice;
    int dieSides;
    int diceMultiplier;
    MoveSetStrat moveSetStrat;
    MoveValStrat moveValStrat;
    CanClearStrat canClearStrat;

    public Settings(){} // Required for Firebase

    public Settings(int boardSize, int pieces, int baseNumberOfDice, int dieSides, int diceMultiplier, MoveSetStrat moveSetStrat, MoveValStrat moveValStrat, CanClearStrat canClearStrat) {
        this.boardSize = boardSize;
        this.pieces = pieces;
        this.baseNumberOfDice = baseNumberOfDice;
        this.dieSides = dieSides;
        this.diceMultiplier = diceMultiplier;
        this.moveSetStrat = moveSetStrat;
        this.moveValStrat = moveValStrat;
        this.canClearStrat = canClearStrat;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getPieces() {
        return pieces;
    }

    public int getBaseNumberOfDice() {
        return baseNumberOfDice;
    }

    public int getDieSides() {
        return dieSides;
    }

    public int getDiceMultiplier() {
        return diceMultiplier;
    }

    public MoveSetStrat getMoveSetStrat() {
        return moveSetStrat;
    }

    public MoveValStrat getMoveValStrat() {
        return moveValStrat;
    }

    public CanClearStrat getCanClearStrat() {
        return canClearStrat;
    }
}
