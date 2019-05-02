package com.progark.emojimon.controller;

import com.progark.emojimon.Emojimon;
import com.progark.emojimon.model.GameBoard;
import com.progark.emojimon.model.Move;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;
import com.progark.emojimon.model.interfaces.Die;


import java.lang.reflect.Modifier;
import java.util.List;

public class GameBoardController {
    private GameBoard gameBoard;
    // boardSize = 25, incl. bar position 0
    public GameBoardController(){
    }

    public int getBoardSize(){return gameBoard.getBoardSize();}


    //create gameboard with standard settings
    public void createStandardGameBoard(){
        gameBoard = new GameBoard();
    }

    //create gameboard with given size
    public void createGameBoard(int boardSize){
        //check if boardsize is valid
        if(boardSize % 2 != 0){
            throw new IllegalArgumentException(String.format("Board size %d is invalid. Size must be divisible by 2. ", boardSize));
        }
        else{
            gameBoard = new GameBoard(boardSize);
        }

    }

    // TODO: choose dice
    // create gameboard with given size and pieces
    public void createDynamicBoard(int boardSize, int piecesPerPlayer){

        if(boardSize % 2 != 0){ //check if boardsize is valid
            throw new IllegalArgumentException(String.format("Board size %d is invalid. Size must be divisible by 2. ", boardSize));
        }
        // check if amount of pieces is valid
        else if (piecesPerPlayer < 15){
            throw new IllegalArgumentException(String.format("Pieces per player %d is invalid. Can't be less than 15", piecesPerPlayer));
        }
        else if ((piecesPerPlayer - 15) % 4 != 0){
            throw new IllegalArgumentException(String.format("Pieces per player %d is invalid. The rest must be 0, i.e divisible by 4.", piecesPerPlayer));
        }
        else{
            gameBoard = new GameBoard(boardSize, piecesPerPlayer, 2, 6, 2, "BASIC", "BASIC", "BASIC");
        }
    }

    public List<Position> getBoardPositions(){
        return gameBoard.getBoardPositions();
    }


    public List<Die> getDieList(){
        return gameBoard.getDice().getDieList();
    }

    public void doMove(Move move){
        gameBoard.movePiece(move);
    }

    // TODO: move to PlayerController?
    public void rollDice(){
        gameBoard.rollDice();
    }

}
