package com.progark.emojimon.controller;

import com.badlogic.gdx.Game;
import com.progark.emojimon.GameManager;
import com.progark.emojimon.model.GameBoard;
import com.progark.emojimon.model.Move;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;
import com.progark.emojimon.model.fireBaseData.Converter;
import com.progark.emojimon.model.fireBaseData.LastTurnData;
import com.progark.emojimon.model.fireBaseData.Settings;
import com.progark.emojimon.model.interfaces.Die;


import java.util.List;
import java.util.concurrent.TimeUnit;

public class GameBoardController {
    private GameBoard gameBoard;

    // boardSize = 25, incl. bar position 0
    public GameBoardController() {
    }

    //create gameboard with standard settings
    public void createStandardGameBoard() {
        gameBoard = new GameBoard();
    }

    //create gameboard with given size
    public void createGameBoard(int boardSize) {
        validateGameBoard(boardSize, true); //check if boardsize is valid
        gameBoard = new GameBoard(boardSize);
    }

    // create gameboard with given size and pieces
    public void createDynamicGameBoard(Settings settings) {
        validateGameBoard(settings.getBoardSize(), true);
        validatePieces(settings.getPiecesPerPlayer(), true);
        gameBoard = new GameBoard(settings);
    }

    // validation methods
    private boolean validateGameBoard(int boardSize, boolean throwException) {
        if (boardSize % 4 != 0) { //check if boardsize is valid
            if (throwException) {
                throw new IllegalArgumentException(String.format("Board size %d is invalid. Size must be divisible by 4. ", boardSize));
            }
            return false;
        }
        return true;
    }

    private boolean validatePieces(int piecesPerPlayer, boolean throwException) {
        if (piecesPerPlayer < 15 || piecesPerPlayer > 30) { // check if amount of pieces is valid (between 15-30)
            if (throwException) {
                throw new IllegalArgumentException(String.format("Pieces per player %d is invalid. Can't be less than 15 or more than 30.", piecesPerPlayer));
            }
            return false;
        }
        return true;
    }

    // getters
    public List<Position> getBoardPositions() {
        return gameBoard.getBoardPositions();
    }

    public List<Die> getDieList() {
        return gameBoard.getDice().getDieList();
    }

    public int getBoardSize() {
        return gameBoard.getBoardSize();
    }

    // methods
    public void doMove(Move move, boolean isItLastTurnMove) {
        gameBoard.movePiece(move, isItLastTurnMove);

        //end turn if not simulating move and no more moves available
        if(!isItLastTurnMove && getMoves(GameManager.GetInstance().getLocalPlayerIndex()).size()==0){
            endTurn();
        }
    }

    // move to PlayerController?
    public void rollDice() {
        gameBoard.rollDice();
        if (getMoves(GameManager.GetInstance().getLocalPlayerIndex()).size() == 0) {
            endTurn();
        }
    }


    public void showLastTurn(LastTurnData lastTurn) {
        if(lastTurn.getActions() != null){
            //Set dices
            for (int i = 0; i < 2; i++) {
                Die die = getDieList().get(i);
                int dieValue = lastTurn.getDices().get(i);
                System.out.println("DieValue: " + dieValue);
                die.setValue(dieValue);
            }

            //Update gameboard with moves
            List<Move> moves = Converter.fromListToMoves(lastTurn.getActions());
            boolean isItLastTurnMove = true;
            for (Move move : moves) {
                doMove(move, isItLastTurnMove);


                //TODO: REMOVE if-statement on line 111
                if(!GameManager.GetInstance().simulateGame){
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();  // set interrupt flag
                    }
                }

            }
        }
    }


    public List<Move> getMoves(int playerindex) {
        if (playerindex == 0) {
            return gameBoard.getPlayer0Moves();
        } else {
            return gameBoard.getPlayer1Moves();
        }
    }

    public void endTurn(){
        GameManager.GetInstance().endTurn();
    }

    public Position getPlayerGoal(int index){
        return gameBoard.getPlayerGoal(index);
    }

    public Player getPlayer(int index){
        if(index == 0){
            return gameBoard.getPlayer0();
        }
        else if(index == 1){
            return gameBoard.getPlayer1();
        }
        else{
            throw new IllegalArgumentException("Invalid player index: " + index);
        }
    }

    public void emptyLastTurnMoves () {
        gameBoard.emptyCurrentTurnMoves();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}

