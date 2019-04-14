package com.progark.emojimon.model;

import com.progark.emojimon.model.factories.CanClearStrategyFactory;
import com.progark.emojimon.model.factories.MoveSetStrategyFactory;
import com.progark.emojimon.model.interfaces.DiceMultiplicationStrategy;
import com.progark.emojimon.model.factories.MoveValidationStrategyFactory;
import com.progark.emojimon.model.interfaces.Die;
import com.progark.emojimon.model.strategyPattern.CanClearStrategy;
import com.progark.emojimon.model.strategyPattern.MoveSetStrategy;
import com.progark.emojimon.model.strategyPattern.MoveValidationStrategy;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private Player player0;
    private Player player1;
    private List<Die> dice;
    private DiceMultiplicationStrategy diceMultiplicationStrategy;
    //includes all board positions indexed from bottom right to top right
    private List<Position> boardPositions;// position 0: our bar
    private int boardSize;
    private List<Move> currentTurnMoves; //TODO empty it out when the turn changes

    private Position bar;
    private Player inBar;

    private int blot = 1; // blot: piece/s that can be thrown out to bar, standard 1

    //constructors
    //creates standard gameboard
    public GameBoard(){
        this(24, 6, "BASIC");
    }

    // strategies
    private MoveSetStrategy moveSetStrategy; //
    private MoveValidationStrategy moveValidationStrategy;
    private CanClearStrategy canClearStrategy;
    //all pieces must be in goalSize before being able to be cleared off
    private int goalSize;
    private int pieces;


    //create gameboard given boardsize, goalsize
    public GameBoard(int boardSize, int goalSize, String moveSetStrategy){
        this (boardSize, goalSize, 15);
    }

    //constructor
    //(currently creating standard gameboard)
    public GameBoard(int boardSize, int goalSize, int pieces){
        this.pieces = pieces;

        // Choose moveset strategy
        moveSetStrategy = MoveSetStrategyFactory.GetMoveSet("BASIC", blot);

        // Choose move validation strategy
        // TODO: add blot to move validation
        moveValidationStrategy = MoveValidationStrategyFactory.getMoveValidationStrategy("BASIC");

        // Choose can clear strategy
        canClearStrategy = CanClearStrategyFactory.getCanClearStrategy("BASIC");


        this.boardSize = boardSize;

        //create players
        //homearea of player0 will be last goalSize indices of board
        //homearea of player1 will be first goalSize indices of board
        player0 = new Player(pieces,boardSize - goalSize, boardSize-1, true, moveValidationStrategy, canClearStrategy, blot, true);
        player1 = new Player(pieces, 0, goalSize-1, false, moveValidationStrategy, canClearStrategy, blot, false);

        //create all positions (including bar at position 0)
        boardPositions = new ArrayList<Position>();
        for(int i = 0; i < boardSize+1; i++){
            Position p = new Position(i);
            boardPositions.add(p);
        }

        bar = boardPositions.get(0);

        //create dice
        dice = new ArrayList<Die>();
        Die d1 = new SixSidedDie();
        Die d2 = new SixSidedDie();
        dice.add(d1);
        dice.add(d2);

        //create pieces
        //TODO: Should number of pieces and piece placement strategy be choosable for the player?
        //player0
        //place white pieces according to standard piece placements
        boardPositions.get(1).addPieces(2);
        boardPositions.get(1).setOwner(player0);
        boardPositions.get(12).addPieces(5);
        boardPositions.get(12).setOwner(player0);
        boardPositions.get(17).addPieces(3);
        boardPositions.get(17).setOwner(player0);
        boardPositions.get(19).addPieces(5);
        boardPositions.get(19).setOwner(player0);


        //place player1 pieces according to standard piece placements
        boardPositions.get(24).addPieces(2);
        boardPositions.get(24).setOwner(player1);
        boardPositions.get(13).addPieces(5);
        boardPositions.get(13).setOwner(player1);
        boardPositions.get(8).addPieces(3);
        boardPositions.get(8).setOwner(player1);
        boardPositions.get(6).addPieces(5);
        boardPositions.get(6).setOwner(player1);


    }


    public boolean movePiece(Move move){
        return moveSetStrategy.doMove(move, boardPositions);
    }

    public void rollDice(Player player){
        for(int i = 0; i < dice.size(); i++){
            dice.get(i).roll();
        }
        player.setDice(dice);
    }

    //region GETTERS AND SETTERS

    public int getBoardSize(){
        return boardSize;
    }
    public List<Position> getBoardPositions(){
        return boardPositions;
    }

    public List<Die> getDice(){
        return dice;
    }

    //TODO: logic for dice move
    public Move getPlayer0BarMove(){
        return player0.getAvailableBarMove(dice, boardPositions);
    }

    public List<Move> getPlayer0Moves(){
        return player0.getAvailableMoves(dice, boardPositions);
    }

    public Move getPlayer1BarMoves(){
        return player1.getAvailableBarMove(dice, boardPositions);
    }

    public List<Move> getPlayer1Moves(){
        return player1.getAvailableMoves(dice, boardPositions);
    }

    public Player getPlayer0() {
        return player0;
    }

    public Player getPlayer1() {
        return player1;
    }
    //endregion
}
