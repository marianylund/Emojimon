package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.DiceRule;
import com.progark.emojimon.model.interfaces.Die;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private Player player0;
    private Player player1;
    private List<Die> dice;
    private DiceRule diceRule;
    //includes all board positions indexed from bottom right to top right
    private List<Position> boardPositions;
    private Position bar;
    private int boardSize;

    //constructor
    //(currently creating standard gameboard)
    public GameBoard(int boardSize){
        //create players
        player0 = new Player(18, 23, true);
        player1 = new Player(0, 5, false);
        boardPositions = new ArrayList<Position>();
        this.boardSize = boardSize;
        //create all positions
        for(int i = 0; i < boardSize; i++){
            Position p = new Position(i);
            boardPositions.add(p);
        }

        //create dice
        dice = new ArrayList<Die>();
        Die d1 = new SixSidedDie();
        Die d2 = new SixSidedDie();
        dice.add(d1);
        dice.add(d2);

        //create pieces
        //player0
        //place white pieces according to standard piece placements
        for(int i = 0; i < 15; i++){
            boardPositions.get(0).addPieces(2);
            boardPositions.get(0).setOwner(player0);
            boardPositions.get(11).addPieces(5);
            boardPositions.get(11).setOwner(player0);
            boardPositions.get(16).addPieces(3);
            boardPositions.get(16).setOwner(player0);
            boardPositions.get(18).addPieces(5);
            boardPositions.get(18).setOwner(player0);
        }

        //place player1 pieces according to standard piece placements
        for(int i = 0; i < 15; i++){
            boardPositions.get(23).addPieces(2);
            boardPositions.get(23).setOwner(player1);
            boardPositions.get(12).addPieces(5);
            boardPositions.get(12).setOwner(player1);
            boardPositions.get(7).addPieces(3);
            boardPositions.get(7).setOwner(player1);
            boardPositions.get(5).addPieces(5);
            boardPositions.get(5).setOwner(player1);
        }

    }

    public void movePiece(Move move){
        Position startPosition = boardPositions.get(move.startPosition);
        Position endPosition = boardPositions.get(move.endPosition);

        //check if endPosition is owned by other player
        if(endPosition.getOwner() != startPosition.getOwner()){
            //TODO: move pieces on endposition to bar
        }

        startPosition.addPieces(1);
        startPosition.removePieces(1);
        endPosition.addPieces(1);
    }

    public void rollDice(){
        for(int i = 0; i < dice.size(); i++){
            dice.get(i).Roll();
        }
    }

    public int getBoardSize(){
        return boardSize;
    }

    public List<Position> getBoardPositions(){
        return boardPositions;
    }

    public List<Die> getDice(){
        return dice;
    }

    public Position getBar(){
        return bar;
    }

    public List<Move> getPlayer0Moves(){
        return player0.getAvailableMoves(dice, boardPositions, bar);
    }

    public List<Move> getPlayer1Moves(){
        return player1.getAvailableMoves(dice, boardPositions, bar);
    }
}
