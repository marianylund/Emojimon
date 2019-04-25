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
    //includes all board positions indexed from bottom right to top right
    private List<Position> boardPositions;// position 0: our bar
    private int boardSize;
    private List<Move> currentTurnMoves; //TODO empty it out when the turn changes

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
        this(24, 15,"BASIC", "BASIC", "BASIC");
    }

    public GameBoard(int boardSize){
        this(boardSize, 15,"BASIC", "BASIC", "BASIC");
    }

    //create dynamic board
    public GameBoard(int boardSize, int pieces, String moveSetStrategyID, String moveValidationStrategyID, String canClearStrategyID){
        this.pieces = pieces;

        // Choose moveset strategy
        moveSetStrategy = MoveSetStrategyFactory.GetMoveSet(moveSetStrategyID, blot);

        // Choose move validation strategy
        // TODO: add blot to move validation
        moveValidationStrategy = MoveValidationStrategyFactory.getMoveValidationStrategy(moveValidationStrategyID);

        // Choose can clear strategy
        canClearStrategy = CanClearStrategyFactory.getCanClearStrategy(canClearStrategyID);


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
        dice = new ArrayList<Die>();
        Die d1 = new SixSidedDie();
        Die d2 = new SixSidedDie();
        dice.add(d1);
        dice.add(d2);

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
        return moveSetStrategy.doMove(move, boardPositions);
    }

    public void rollDice(){
        for(int i = 0; i < dice.size(); i++){
            dice.get(i).roll();
            dice.get(i).setUsed(false);
        }
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

    //checks for available moves on bar if player has pieces on bar
    //otherwise checks for available moves on board
    public List<Move> getPlayer0Moves(){
        if(bar.getOwner() == player0 && bar.getPieceCount() > 0){
            return player0.getAvailableBarMoves(dice, boardPositions, player1.getHomeAreaStartIndex(), player1.getHomeAreaEndIndex());
        }
        else{
            return player0.getAvailableBoardMoves(dice, boardPositions);
        }
    }

    //checks for available moves on bar if player has pieces on bar
    //otherwise checks for available moves on board
    public List<Move> getPlayer1Moves(){
        if(bar.getOwner() == player1 && bar.getPieceCount() > 0){
            return player1.getAvailableBarMoves(dice, boardPositions, player0.getHomeAreaStartIndex(), player0.getHomeAreaEndIndex());
        }
        else{
            return player1.getAvailableBoardMoves(dice, boardPositions);
        }
    }

    public Player getPlayer0() {
        return player0;
    }

    public Player getPlayer1() {
        return player1;
    }
    //endregion
}
