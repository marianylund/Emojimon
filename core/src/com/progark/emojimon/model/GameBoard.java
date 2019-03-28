package com.progark.emojimon.model;

import com.progark.emojimon.model.factories.MoveSetStrategyFactory;
import com.progark.emojimon.model.interfaces.DiceRule;
import com.progark.emojimon.model.interfaces.Die;
import com.progark.emojimon.model.strategyPattern.MoveSetStrategy;

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
    private Player inBar;
    private int boardSize;
    private int blot = 1; // blot: piece/s that can be thrown out to bar, standard 1
    private MoveSetStrategy moveSet; //
    private int goalSize;

    //constructors

    //creates standard gameboard
    public GameBoard(){
        this(24, 6, "BASIC");
    }
    //create gameboard given boardsize, goalsize
    public GameBoard(int boardSize, int goalSize, String moveSetStrategy){

        this.boardSize = boardSize;

        //create players
        //homearea of player0 will be last goalSize indices of board
        //homeare of player1 will be first goalSize indices of board
        player0 = new Player(boardSize - goalSize, boardSize-1, true);
        player1 = new Player(0, goalSize-1, false);

        //create all positions
        boardPositions = new ArrayList<Position>();
        for(int i = 0; i < boardSize; i++){
            Position p = new Position(i);
            boardPositions.add(p);
        }
        bar = new Position(boardSize);

        //create dice
        dice = new ArrayList<Die>();
        Die d1 = new SixSidedDie();
        Die d2 = new SixSidedDie();
        dice.add(d1);
        dice.add(d2);

        // Choose moveset strategy
        MoveSetStrategyFactory moveSetFactory = new MoveSetStrategyFactory();
        moveSet= moveSetFactory.GetMoveSet(moveSetStrategy, blot);

        //TODO: add chosen move validation strategy (factory)
        //TODO: add chosen can clear strategy (factory)


        //create pieces
        //TODO: Should number of pieces and piece placement strategy be choosable for the player?
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
        moveSet.calculateMove(move, boardPositions, bar, inBar);
    }

    public void rollDice(){
        for(int i = 0; i < dice.size(); i++){
            dice.get(i).roll();
        }
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
