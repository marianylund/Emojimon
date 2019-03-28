package com.progark.emojimon.controller;

import com.progark.emojimon.Emojimon;
import com.progark.emojimon.model.GameBoard;
import com.progark.emojimon.model.Move;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;
import com.progark.emojimon.model.interfaces.Die;


import java.util.List;

public class GameBoardController {
    private GameBoard gameBoard;

    public GameBoardController(){
    }

    public void createGameBoard(int boardSize, int goalSize, String moveSetStrategy){
        gameBoard = new GameBoard(boardSize, goalSize, moveSetStrategy);
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

    public void MovePiece(Move move){
        gameBoard.movePiece(move);
    }

}
