package com.progark.emojimon.controller;

import com.progark.emojimon.Emojimon;
import com.progark.emojimon.model.GameBoard;
import com.progark.emojimon.model.Position;
import com.progark.emojimon.model.interfaces.Die;


import java.util.List;

public class GameBoardController {
    private GameBoard gameBoard;

    public GameBoardController(){
        gameBoard = new GameBoard(24);
    }

    public int getBoardSize(){return gameBoard.getBoardSize();}

    public List<Position> getBoardPositions(){
        return gameBoard.getBoardPositions();
    }

   /*public Position getWhitePlayerBar(){
        return gameBoard.getPlayer1Bar();
    }

    public Position getBlackPlayerBar(){
        return gameBoard.getPlayer0Bar();
    }
    */
    public List<Die> getDice(){
        return gameBoard.getDice();
    }

    public void rollDice(){
        gameBoard.rollDice();
    }


}
