package com.progark.emojimon.controller;

import com.progark.emojimon.Emojimon;
import com.progark.emojimon.GameManager;
import com.progark.emojimon.model.GameBoard;
import com.progark.emojimon.model.Move;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;
import com.progark.emojimon.model.fireBaseData.Converter;
import com.progark.emojimon.model.fireBaseData.LastTurnData;
import com.progark.emojimon.model.interfaces.Die;


import java.lang.reflect.Modifier;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        if(boardSize % 4 != 0){
            throw new IllegalArgumentException(String.format("Board size %d is invalid. Size must be divisible by 4. ", boardSize));
        }
        else{
            gameBoard = new GameBoard(boardSize);
        }

    }

    // TODO: add possibility to choose dice
    // create gameboard with given size and pieces
    public void createDynamicBoard(int boardSize, int piecesPerPlayer){

        if(boardSize % 4 != 0){ //check if boardsize is valid
            throw new IllegalArgumentException(String.format("Board size %d is invalid. Size must be divisible by 4. ", boardSize));
        }
        // check if amount of pieces is valid (between 15-30)
        else if (piecesPerPlayer < 15){
            throw new IllegalArgumentException(String.format("Pieces per player %d is invalid. Can't be less than 15", piecesPerPlayer));
        }
        else if (piecesPerPlayer > 30){
            throw new IllegalArgumentException(String.format("Pieces per player %d is invalid. Cannot have over 30 pieces per player.", piecesPerPlayer));
        }
        else{
            gameBoard = new GameBoard(boardSize, piecesPerPlayer, 2, 6, 2, "BASIC", "BASIC", "BASIC", "BASIC");
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

    // move to PlayerController?
    public void rollDice(){
        gameBoard.rollDice();
    }

    public void showLastTurn(LastTurnData lastTurn) {
        //Set dices
        for (int i = 0; i < 2; i++) {
            Die die = getDieList().get(i);
            int dieValue = lastTurn.getDices().get(i);
            System.out.println("DieValue" + dieValue);
            die.setValue(dieValue);
        }

        //Update gameboard with moves
        List<Move> moves = Converter.fromListToMoves(lastTurn.getActions());
        for (Move move : moves) {
            doMove(move);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();  // set interrupt flag
            }
        }
    }

    public void endTurn(Player player){
        if(player.isDone()){
            // TODO End Game
        }else{
            // Push Last turn data
            FBC.I().get().addLastTurnByGameID(GameManager.GetInstance().getGameID(), !player.isCreator(),
                    Converter.fromDiceToList(gameBoard.getDice().getDieList()),
                    Converter.fromMovesToList(gameBoard.getCurrentTurnMoves()));
            gameBoard.emptyCurrentTurnMoves();
        }
    }
}
