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
        //check if boardsize is valid
        if(boardSize % 2 != 0){
            throw new IllegalArgumentException(String.format("Board size %d is invalid. Size must be divisible by 2. ", boardSize));
        }
        else{
            gameBoard = new GameBoard(boardSize, goalSize, moveSetStrategy);
        }
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
