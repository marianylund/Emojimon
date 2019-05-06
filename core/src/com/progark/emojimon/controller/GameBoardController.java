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

    //create gameboard with standard settings
    public void createStandardGameBoard(){
        gameBoard = new GameBoard();
    }

    //create gameboard with given size
    public void createGameBoard(int boardSize){
        validateGameBoard(boardSize, true); //check if boardsize is valid
        gameBoard = new GameBoard(boardSize);
    }

    // create gameboard with given size and pieces
    public void createDynamicGameBoard(int boardSize, int piecesPerPlayer, int baseNumberOfDice, int dieSides, int diceMultiplier){
        validateGameBoard(boardSize, true);
        validatePieces(piecesPerPlayer, true);
        gameBoard = new GameBoard(boardSize, piecesPerPlayer, baseNumberOfDice, dieSides, diceMultiplier, "BASIC", "BASIC", "BASIC", "BASIC");
    }

    // validation methods
    private boolean validateGameBoard(int boardSize, boolean throwException) {
        if(boardSize % 4 != 0){ //check if boardsize is valid
            if (throwException){
                throw new IllegalArgumentException(String.format("Board size %d is invalid. Size must be divisible by 4. ", boardSize));
            }
            return false;
        }
        return true;
    }

    private boolean validatePieces(int piecesPerPlayer, boolean throwException){
        if (piecesPerPlayer < 15 || piecesPerPlayer > 30){ // check if amount of pieces is valid (between 15-30)
            if (throwException) {
                throw new IllegalArgumentException(String.format("Pieces per player %d is invalid. Can't be less than 15 or more than 30.", piecesPerPlayer));
            }
            return false;
        }
        return true;
    }

    // getters
    public List<Position> getBoardPositions(){
        return gameBoard.getBoardPositions();
    }

    public List<Die> getDieList(){
        return gameBoard.getDice().getDieList();
    }

    public int getBoardSize(){return gameBoard.getBoardSize();}

    // methods
    public void doMove(Move move){
        gameBoard.movePiece(move);
    }

    // move to PlayerController?
    public void rollDice(){
        gameBoard.rollDice();
    }

}
