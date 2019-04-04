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
    private List<Position> boardPositions;
    private int boardSize;
    private List<Move> currentTurnMoves; //TODO emty it out when the turn changes

    private Position bar;
    private Player inBar;
    private int blot = 1; // blot: piece/s that can be thrown out to bar, standard 1
    //constructors
    //creates standard gameboard
    public GameBoard(){
        this(24, 6, "BASIC");
    }

    // strategies
    private MoveSetStrategy moveSet; //
    private MoveValidationStrategy moveValidation;
    private CanClearStrategy canClear;
    private int goalSize;


    //create gameboard given boardsize, goalsize
    public GameBoard(int boardSize, int goalSize, String moveSetStrategy){

    }
    //constructor
    //(currently creating standard gameboard)
    public GameBoard(int boardSize, int goalSize){

        // Choose moveset strategy
        MoveSetStrategyFactory moveSetFactory = new MoveSetStrategyFactory();
        moveSet = moveSetFactory.GetMoveSet("BASIC", blot);

        // Choose move validation strategy
        // TODO: add blot to move validation
        MoveValidationStrategyFactory moveValidationFactory = new MoveValidationStrategyFactory();
        moveValidation = moveValidationFactory.getMoveValidationStrategy("BASIC");

        // Choose can clear strategy
        CanClearStrategyFactory canClearFactory = new CanClearStrategyFactory();
        canClear = canClearFactory.getCanClearStrategy("BASIC");

        this.boardSize = boardSize;

        //create players
        //homearea of player0 will be last goalSize indices of board
        //homeare of player1 will be first goalSize indices of board
        player0 = new Player(boardSize - goalSize, boardSize-1, true, moveValidation, canClear, true);
        player1 = new Player(0, goalSize-1, false, moveValidation, canClear, false);

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


    public boolean movePiece(Move move){
        currentTurnMoves.add(move);
        return moveSet.doMove(move, boardPositions, bar, inBar);
    }

    public void rollDice(Player player){
        for(int i = 0; i < dice.size(); i++){
            dice.get(i).roll();
        }
        player.setDice(dice);
    }

    //region GETTERS AND SETTERS

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

    public Player getPlayer0() {
        return player0;
    }

    public Player getPlayer1() {
        return player1;
    }
    //endregion
}
