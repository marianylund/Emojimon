package com.progark.emojimon.model;

import com.progark.emojimon.model.factories.CanClearStrategyFactory;
import com.progark.emojimon.model.factories.DieFactory;
import com.progark.emojimon.model.factories.MoveSetStrategyFactory;
import com.progark.emojimon.model.interfaces.DiceMultiplicationStrategy;
import com.progark.emojimon.model.factories.MoveValidationStrategyFactory;
import com.progark.emojimon.model.interfaces.Die;
import com.progark.emojimon.model.strategyPattern.CanClearStrategy;
import com.progark.emojimon.model.strategyPattern.MoveSetStrategy;
import com.progark.emojimon.model.strategyPattern.MoveValidationStrategy;
import com.progark.emojimon.model.factories.MoveValidationStrategyFactory.MoveValStrat;
import com.progark.emojimon.model.factories.MoveSetStrategyFactory.MoveSetStrat;
import com.progark.emojimon.model.factories.CanClearStrategyFactory.CanClearStrat;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private Player player0;
    private Player player1;
    //includes all board positions indexed from bottom right to top right
    private List<Position> boardPositions;// position 0: our bar
    private int boardSize;
    private List<Move> currentTurnMoves; // moves done during this turn, it is emptied when the turn is ended

    //dice
    private Dice dice;

    //all pieces must be in goalSize before being able to be cleared off
    private int pieces;

    private Position bar;
    private Player inBar;

    private int blot = 1; // blot: piece/s that can be thrown out to bar, standard 1

    // strategies
    private DiceMultiplicationStrategy diceMultiplicationStrategy;
    private MoveSetStrategy moveSetStrategy; //
    private MoveValidationStrategy moveValidationStrategy;
    private CanClearStrategy canClearStrategy;

    //constructors
    //creates standard gameboard
    public GameBoard(){
        this(24, 15,2, 6, 2, MoveSetStrat.BASIC, MoveValStrat.BASIC, CanClearStrat.BASIC);
    }

    public GameBoard(int boardSize){
        this(boardSize, 15, 2, 6, 2, MoveSetStrat.BASIC, MoveValStrat.BASIC, CanClearStrat.BASIC);
    }

    //create dynamic board

    public GameBoard(int boardSize, int pieces, int baseNumberOfDice, int dieSides, int diceMultiplier, MoveSetStrat moveSetStrat, MoveValStrat moveValStrat, CanClearStrat canClearStrat){

        this.pieces = pieces;

        // Choose moveset strategy
        moveSetStrategy = MoveSetStrategyFactory.GetMoveSet(moveSetStrat, blot);

        // Choose move validation strategy
        // TODO: add blot to move validation
        moveValidationStrategy = MoveValidationStrategyFactory.getMoveValidationStrategy(moveValStrat);

        // Choose can clear strategy
        canClearStrategy = CanClearStrategyFactory.getCanClearStrategy(canClearStrat);


        this.boardSize = boardSize;

        //create players
        //homearea of player0 will be first quadrant of the board
        //homearea of player1 will be last quadrant of the board
        player0 = new Player(pieces,1, boardSize/4,false, moveValidationStrategy, canClearStrategy, blot, true);
        player1 = new Player(pieces, boardSize + 1 - (boardSize/4), boardSize, true, moveValidationStrategy, canClearStrategy, blot, false);

        //create all positions (including bar at position 0)
        boardPositions = new ArrayList<Position>();
        for(int i = 0; i < boardSize+1; i++){
            Position p = new Position(i);
            boardPositions.add(p);
        }

        bar = boardPositions.get(0);

        //create dice
        dice = new Dice(baseNumberOfDice, diceMultiplier, dieSides);

        //create pieces
        //TODO: Should number of pieces and piece placement strategy be choosable for the player?
        //player0
        //place white pieces according to standard piece placements
        boardPositions.get(24).addPieces(2);
        boardPositions.get(24).setOwner(player0);
        boardPositions.get(13).addPieces(5);
        boardPositions.get(13).setOwner(player0);
        boardPositions.get(8).addPieces(3);
        boardPositions.get(8).setOwner(player0);
        boardPositions.get(6).addPieces(5);
        boardPositions.get(6).setOwner(player0);


        //place player1 pieces according to standard piece placements
        boardPositions.get(1).addPieces(2);
        boardPositions.get(1).setOwner(player1);
        boardPositions.get(12).addPieces(5);
        boardPositions.get(12).setOwner(player1);
        boardPositions.get(17).addPieces(3);
        boardPositions.get(17).setOwner(player1);
        boardPositions.get(19).addPieces(5);
        boardPositions.get(19).setOwner(player1);


    }


    public boolean movePiece(Move move){
        move.die.setUsed(true);
        currentTurnMoves.add(move);
        return moveSetStrategy.doMove(move, boardPositions);
    }

    public void rollDice(){
        dice.rollDice();
    }

    //region GETTERS AND SETTERS

    public int getBoardSize(){
        return boardSize;
    }
    public List<Position> getBoardPositions(){
        return boardPositions;
    }

    public Dice getDice(){
        return dice;
    }

    //checks for available moves on bar if player has pieces on bar
    //otherwise checks for available moves on board
    public List<Move> getPlayer0Moves(){
        if(bar.getOwner() == player0 && bar.getPieceCount() > 0){
            return player0.getAvailableBarMoves(dice.getDieList(), boardPositions, player1.getHomeAreaStartIndex(), player1.getHomeAreaEndIndex());
        }
        else{
            return player0.getAvailableBoardMoves(dice.getDieList(), boardPositions);
        }
    }

    //checks for available moves on bar if player has pieces on bar
    //otherwise checks for available moves on board
    public List<Move> getPlayer1Moves(){
        if(bar.getOwner() == player1 && bar.getPieceCount() > 0){
            return player1.getAvailableBarMoves(dice.getDieList(), boardPositions, player0.getHomeAreaStartIndex(), player0.getHomeAreaEndIndex());
        }
        else{
            return player1.getAvailableBoardMoves(dice.getDieList(), boardPositions);
        }
    }

    public Player getPlayer0() {
        return player0;
    }

    public Player getPlayer1() {
        return player1;
    }

    public List<Move> getCurrentTurnMoves() {
        return currentTurnMoves;
    }

    public void emptyCurrentTurnMoves(){
        currentTurnMoves.clear();
    }

    //endregion
}
