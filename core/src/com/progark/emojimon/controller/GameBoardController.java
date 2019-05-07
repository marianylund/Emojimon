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
        if(boardSize % 2 != 0){
            throw new IllegalArgumentException(String.format("Board size %d is invalid. Size must be divisible by 2. ", boardSize));
        }
        else{
            gameBoard = new GameBoard(boardSize);
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

    public List<Move> getMoves(int playerindex){
        if(playerindex == 0){
            return gameBoard.getPlayer0Moves();
        }
        else {
            return gameBoard.getPlayer1Moves();
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

}
