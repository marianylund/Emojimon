package com.progark.emojimon.model.fireBaseData;

import com.progark.emojimon.model.factories.MoveValidationStrategyFactory.MoveValStrat;
import com.progark.emojimon.model.factories.MoveSetStrategyFactory.MoveSetStrat;
import com.progark.emojimon.model.factories.CanClearStrategyFactory.CanClearStrat;
import com.progark.emojimon.model.factories.StartPiecePlacementStrategyFactory.PiecePlacementStrat;

public class Settings {
    String lobbyName;
    int boardSize;
    int piecesPerPlayer;
    int baseNumberOfDice;
    int dieSides;
    int diceMultiplier;
    MoveSetStrat moveSetStrat;
    MoveValStrat moveValStrat;
    CanClearStrat canClearStrat;
    PiecePlacementStrat piecePlacementStrat;

    public Settings(){} // Required for Firebase

    public Settings(String lobbyName, int boardSize, int piecesPerPlayer, int baseNumberOfDice, int dieSides, int diceMultiplier, MoveSetStrat moveSetStrat, MoveValStrat moveValStrat, CanClearStrat canClearStrat, PiecePlacementStrat piecePlacementStrat) {
        this.lobbyName = lobbyName;
        this.boardSize = boardSize;
        this.piecesPerPlayer = piecesPerPlayer;
        this.baseNumberOfDice = baseNumberOfDice;
        this.dieSides = dieSides;
        this.diceMultiplier = diceMultiplier;
        this.moveSetStrat = moveSetStrat;
        this.moveValStrat = moveValStrat;
        this.canClearStrat = canClearStrat;
        this.piecePlacementStrat = piecePlacementStrat;
    }

    public String getLobbyName() {
        return lobbyName;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getPiecesPerPlayer() {
        return piecesPerPlayer;
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

    public PiecePlacementStrat getPiecePlacementStrat() {
        return piecePlacementStrat;
    }

    @Override
    public String toString() {
        return "Settings{" +
                "lobbyName='" + lobbyName + '\'' +
                ", boardSize=" + boardSize +
                ", piecesPerPlayer=" + piecesPerPlayer +
                ", baseNumberOfDice=" + baseNumberOfDice +
                ", dieSides=" + dieSides +
                ", diceMultiplier=" + diceMultiplier +
                ", moveSetStrat=" + moveSetStrat +
                ", moveValStrat=" + moveValStrat +
                ", canClearStrat=" + canClearStrat +
                ", piecePlacementStrat=" + piecePlacementStrat +
                '}';
    }
}
