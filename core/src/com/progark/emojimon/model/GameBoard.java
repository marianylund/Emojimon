package com.progark.emojimon.model;

import com.progark.emojimon.model.factories.CanClearStrategyFactory;
import com.progark.emojimon.model.factories.MoveSetStrategyFactory;
import com.progark.emojimon.model.factories.MoveValidationStrategyFactory;
import com.progark.emojimon.model.interfaces.DiceRule;
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
    private DiceRule diceRule;
    //includes all board positions indexed from bottom right to top right
    private List<Position> boardPositions; // position 0: our bar
    private Position bar;
    private Player inBar;
    private int boardSize;
    private int blot = 1; // blot: piece/s that can be thrown out to bar, standard 1
    // strategies
    private MoveSetStrategy moveSet; //
    private MoveValidationStrategy moveValidation;
    private CanClearStrategy canClear;
    private int goalSize;
    private int pieces;

    //constructor
    //(currently creating standard gameboard)
    public GameBoard(int boardSize, int goalSize, int pieces){
        this.pieces = pieces;

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

        //create players
        player0 = new Player(pieces,18, 23, true, moveValidation, canClear);
        player1 = new Player(pieces,0, 5, false, moveValidation, canClear);
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
        for(int i = 0; i < pieces; i++){
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
        for(int i = 0; i < pieces; i++){
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
        return moveSet.doMove(move, boardPositions, bar, inBar);
    }

    public void rollDice(Player player){
        for(int i = 0; i < dice.size(); i++){
            dice.get(i).Roll();
        }
        player.setDice(dice);
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
