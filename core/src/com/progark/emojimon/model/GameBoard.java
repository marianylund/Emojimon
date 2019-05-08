package com.progark.emojimon.model;

import com.progark.emojimon.model.factories.CanClearStrategyFactory;
import com.progark.emojimon.model.factories.MoveSetStrategyFactory;
import com.progark.emojimon.model.factories.StartPiecePlacementStrategyFactory;
import com.progark.emojimon.model.fireBaseData.Settings;
import com.progark.emojimon.model.interfaces.DiceMultiplicationStrategy;
import com.progark.emojimon.model.factories.MoveValidationStrategyFactory;
import com.progark.emojimon.model.strategyPattern.CanClearStrategy;
import com.progark.emojimon.model.strategyPattern.MoveSetStrategy;
import com.progark.emojimon.model.strategyPattern.MoveValidationStrategy;
import com.progark.emojimon.model.factories.MoveValidationStrategyFactory.MoveValStrat;
import com.progark.emojimon.model.factories.MoveSetStrategyFactory.MoveSetStrat;
import com.progark.emojimon.model.factories.CanClearStrategyFactory.CanClearStrat;
import com.progark.emojimon.model.strategyPattern.StartPiecePlacementStrategy;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private Player player0;
    private Player player1;
    //includes all board positions indexed from bottom right to top right

    private List<Position> boardPositions;// position 0: our bar
    private Position bar;

    private int boardSize;
    //all pieces must be in goalSize before being able to be cleared off
    private int piecesPerPlayer;
    private List<Move> currentTurnMoves; // moves done during this turn, it is emptied when the turn is ended

    //dice
    private Dice dice;

    private int blot = 1; // blot is how many of the opposing pieces in an endposition can be thrown of to bar

    // strategies
    private DiceMultiplicationStrategy diceMultiplicationStrategy;
    private MoveSetStrategy moveSetStrategy; //
    private MoveValidationStrategy moveValidationStrategy;
    private CanClearStrategy canClearStrategy;
    private StartPiecePlacementStrategy startPiecePlacementStrategy;

    private static Settings standardSettings = new Settings(24, 15, 2, 6, 2, MoveSetStrat.BASIC.BASIC, MoveValStrat.BASIC, CanClearStrat.BASIC, StartPiecePlacementStrategyFactory.PiecePlacementStrat.BASIC);
    //constructors
    //creates standard gameboard
    public GameBoard(){
        this(standardSettings);
    }

    public GameBoard(int boardSize){
        this(standardSettings);
    }

    //create dynamic board

    public GameBoard(Settings settings){
        this.piecesPerPlayer = settings.getPiecesPerPlayer();

        currentTurnMoves = new ArrayList<Move>();

        // Choose moveset strategy
        moveSetStrategy = MoveSetStrategyFactory.GetMoveSet(settings.getMoveSetStrat(), blot);

        // Choose move validation strategy

        moveValidationStrategy = MoveValidationStrategyFactory.getMoveValidationStrategy(settings.getMoveValStrat(), blot);

        // Choose can clear strategy
        canClearStrategy = CanClearStrategyFactory.getCanClearStrategy(settings.getCanClearStrat());

        // Choose a startPiecePlacementStrategy
        startPiecePlacementStrategy = StartPiecePlacementStrategyFactory.getPiecePlacementStrategy(settings.getPiecePlacementStrat());

        this.boardSize = settings.getBoardSize();

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
        player0 = new Player(piecesPerPlayer, 1, boardSize/4, player0Goal, false, moveValidationStrategy, canClearStrategy, true);
        player1 = new Player(piecesPerPlayer, boardSize + 1 - (boardSize/4), boardSize, player1Goal, true, moveValidationStrategy, canClearStrategy, false);
        player0Goal.setOwner(player0);
        player1Goal.setOwner(player1);

        //create dice
        dice = new Dice(settings.getBaseNumberOfDice(), settings.getDiceMultiplier(), settings.getDieSides());

        // add pieces to board
        startPiecePlacementStrategy.placeStartPieces(boardSize, piecesPerPlayer, boardPositions, player0, player1, piecesPerPlayer);

    }


    public boolean movePiece(Move move){
        if(move.die != null){
            move.die.setUsed(true);
        }
        currentTurnMoves.add(move);

        //check if move is to either player's goal (outside of boardpositions)
        if(move.endPosition >= boardPositions.size()){
            //move piece to goal
            if(move.endPosition == getPlayerGoal(0).getPositionIndex()){
                //move to goal 0
                boardPositions.get(move.startPosition).removePieces(1);
                getPlayerGoal(0).addPieces(1);
                return true;
            }
            else if(move.endPosition == getPlayerGoal(1).getPositionIndex()){
                //move to goal 1
                boardPositions.get(move.startPosition).removePieces(1);
                getPlayerGoal(1).addPieces(1);
                return true;
            }
            else{
                return false;
            }
        }
        else{
            //do board move
            return moveSetStrategy.doMove(move, boardPositions);
        }
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

    public List<Move> getCurrentTurnMoves() {
        return currentTurnMoves;
    }

    public void emptyCurrentTurnMoves(){
        currentTurnMoves.clear();
    }

    //endregion
}
