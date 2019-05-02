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
import com.sun.org.apache.xpath.internal.operations.Mult;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private Player player0;
    private Player player1;
    //includes all board positions indexed from bottom right to top right

    private List<Position> boardPositions;// position 0: our bar
    private Position bar;

    private int boardSize;
    private List<Move> currentTurnMoves; //TODO empty it out when the turn changes

    //dice
    private Dice dice;

    //all pieces must be in goalSize before being able to be cleared off
    private int pieces;


    private int blot = 1; // blot: piece/s that can be thrown out to bar, standard 1

    // strategies
    private MoveSetStrategy moveSetStrategy; //
    private MoveValidationStrategy moveValidationStrategy;
    private CanClearStrategy canClearStrategy;

    //constructors
    //creates standard gameboard
    public GameBoard(){
        this(24, 15,2, 6, 2, "BASIC", "BASIC", "BASIC");
    }

    public GameBoard(int boardSize){
        this(boardSize, 15, 2, 6, 2, "BASIC", "BASIC", "BASIC");
    }

    //create dynamic board
    public GameBoard(int boardSize, int pieces, int baseNumberOfDice, int dieSides, int diceMultiplier, String moveSetStrategyID, String moveValidationStrategyID, String canClearStrategyID){
        this.pieces = pieces;

        // Choose moveset strategy
        moveSetStrategy = MoveSetStrategyFactory.GetMoveSet(moveSetStrategyID, blot);

        // Choose move validation strategy
        // TODO: add blot to move validation
        moveValidationStrategy = MoveValidationStrategyFactory.getMoveValidationStrategy(moveValidationStrategyID);

        // Choose can clear strategy
        canClearStrategy = CanClearStrategyFactory.getCanClearStrategy(canClearStrategyID);


        this.boardSize = boardSize;

        //create all positions
        boardPositions = new ArrayList<Position>();

        //create bar at index 0
        bar = new Position(0);
        boardPositions.add(bar);

        //create all board positions
        for(int i = 1; i < boardSize+1; i++){
            Position p = new Position(i);
            boardPositions.add(p);
        }

        //create goal position
        Position player0Goal = new Position(boardSize+1);
        Position player1Goal = new Position(boardSize+2);

        //create players
        //homearea of player0 will be first quadrant of the board
        //homearea of player1 will be last quadrant of the board
        player0 = new Player(pieces,1, boardSize/4, player0Goal, false, moveValidationStrategy, canClearStrategy, blot, true);
        player1 = new Player(pieces, boardSize + 1 - (boardSize/4), boardSize, player1Goal, true, moveValidationStrategy, canClearStrategy, blot, false);

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
        if(move.die != null){
            move.die.setUsed(true);
        }
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

    public Position getPlayerGoal(int playerIndex){
        if(playerIndex == 0) return player0.getGoal();
        else if(playerIndex == 1) return player1.getGoal();
        else{
            throw new IndexOutOfBoundsException("Invalid player index " + playerIndex);
        }
    }
    //endregion
}
