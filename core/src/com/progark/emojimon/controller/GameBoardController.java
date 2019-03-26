package com.progark.emojimon.controller;

import com.progark.emojimon.Emojimon;
import com.progark.emojimon.model.GameBoard;
import com.progark.emojimon.model.Position;
import com.progark.emojimon.model.interfaces.Die;


import java.util.List;

public class GameBoardController {
    private GameBoard gameBoard;

    public GameBoardController(){
        gameBoard = new GameBoard(24, 6);
    }

    public List<Position> getBoardPositions(){
        return gameBoard.getBoardPositions();
    }

    public List<Die> getDice(){
        return gameBoard.getDice();
    }

    public void rollDice(){
        gameBoard.rollDice();
    }


}
