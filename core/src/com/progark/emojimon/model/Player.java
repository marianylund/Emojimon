package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.Die;
import com.progark.emojimon.model.strategyPattern.CanClearStrategy;
import com.progark.emojimon.model.strategyPattern.MoveValidationStrategy;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private boolean creator = false;
    private int homeAreaStartIndex;
    private int homeAreaEndIndex;
    private boolean moveClockwise;
    private MoveValidationStrategy moveValidationStrategy;
    private CanClearStrategy canClearStrategy;
    private int numberOfPieces; // hold count of pieces that haven't been cleared off
    private int blot; // blot: piece/s that can be thrown out to bar
    private Position goal;


    public Player(int numberOfPieces, int homeAreaStartIndex, int homeAreaEndIndex, Position goal, boolean moveClockwise, MoveValidationStrategy moveValidationStrategy, CanClearStrategy canClearStrategy, int blot, boolean isCreator){
        this.numberOfPieces = numberOfPieces;
        this.homeAreaStartIndex = homeAreaStartIndex;
        this.homeAreaEndIndex = homeAreaEndIndex;
        this.goal = goal;
        this.moveClockwise = moveClockwise;
        this.moveValidationStrategy = moveValidationStrategy;
        // set strategies for piece movement
        this.canClearStrategy = canClearStrategy;
        this.blot = blot;
        this.creator = isCreator;
    }

    //Get all available moves from bar (pieces to be entered)
    //Entering involves moving a piece from the bar to a position in the other player's home area
    //See "Hitting and Entering": http://www.bkgm.com/rules.html
    public List<Move> getAvailableBarMoves(List<Die> dice, List<Position> positions, int otherPlayerHomeAreaStartIndex, int otherPlayerHomeAreaEndIndex){
        List<Move> moves = new ArrayList<Move>();

        int barIndex = 0; //barIndex is the first position

        //return empty list if player does not own bar or bar is empty
        if(positions.get(barIndex).getOwner() != this || positions.get(barIndex).getPieceCount() == 0){
            return moves;
        }

        // check for possible moves from bar
        for (int diceIndex = 0; diceIndex < dice.size(); diceIndex++){
            Die die = dice.get(diceIndex);
            //ignore used dice
            if(die.getUsed()) continue;

            int diceValue = die.getValue();

            int endPositionIndex;

            if(moveClockwise){
                endPositionIndex = (otherPlayerHomeAreaStartIndex - 1) + diceValue;
            }
            else{
                endPositionIndex = (otherPlayerHomeAreaEndIndex + 1) - diceValue;
            }

            //ignore move if endposition is outside of other player's home area
            if(endPositionIndex < otherPlayerHomeAreaStartIndex || endPositionIndex > otherPlayerHomeAreaEndIndex){
                continue;
            }

            //ignore move if endposition index is bar or out of bounds
            if(endPositionIndex < 1 || endPositionIndex >= positions.size()){
                continue;
            }

            Position endPosition = positions.get(endPositionIndex);

            //apply move validation strategy to check if move is valid
            if(moveValidationStrategy.isAvailableMove(positions.get(0), endPosition, blot)){
                moves.add(new Move(barIndex, endPositionIndex, die));
            }
        }

        return moves;
    }

    //Get available moves using pieces on the board (i.e. not on bar)
    public List<Move> getAvailableBoardMoves(List<Die> dice, List<Position> positions) {
        List<Move> moves = new ArrayList<Move>();

        //find all possible moves for player given die values in dice
        for(int positionIndex = 1; positionIndex < positions.size(); positionIndex++){
            Position startPosition = positions.get(positionIndex);
            if(startPosition.getOwner() == this){
                //check for possible moves
                for(int diceIndex = 0; diceIndex < dice.size(); diceIndex++){
                    Die die = dice.get(diceIndex);

                    //ignore used dice
                    if(die.getUsed()) continue;

                    int diceValue = die.getValue();

                    int endPositionIndex = moveClockwise ? (positionIndex + diceValue) : (positionIndex - diceValue);

                    Position endPosition = null;

                    //check if player can "clear" pieces off the board
                    if(canClear(positions)) {
                        //moving off the board is valid
                        //if player moves anticlockwise, off the board = position 0
                        //if player moves clockwise, off the board = boardPositions.size
                        if (moveClockwise && endPositionIndex == 0 || !moveClockwise && endPositionIndex == positions.size()) {
                            endPosition = goal;
                        }
                        else{
                            //ignore move if endposition index is bar or out of bounds
                            if(endPositionIndex < 0 || endPositionIndex > positions.size()){
                                continue;
                            }
                        }
                    }
                    else{
                        //ignore if endposition index is bar or out of bounds
                        if(endPositionIndex < 1 || endPositionIndex >= positions.size()){
                            continue;
                        }
                        endPosition = positions.get(endPositionIndex);
                    }

                    //apply move validation strategy to check if move is valid
                    if(moveValidationStrategy.isAvailableMove(startPosition, endPosition, blot)){
                        moves.add(new Move(positionIndex, endPositionIndex, die));
                    }
                }
            }
        }

        return moves;
    }

    //GETTERS
    public int getHomeAreaStartIndex() {
        return homeAreaStartIndex;
    }

    public int getHomeAreaEndIndex() {
        return homeAreaEndIndex;
    }

    public boolean getMoveClockwise(){
        return moveClockwise;
    }

    public Position getGoal(){
        return goal;
    }

    // returns whether player has cleared all of their pieces, i.e. won
    public boolean isDone() {
        return (goal.getPieceCount() == numberOfPieces);
    }

    //returns whether player is in a position to start clearing pieces
    public boolean canClear(List<Position> boardPositions) {
        return canClearStrategy.canClear(this, boardPositions);
    }

    // returns whether move is available
    public boolean isAvailableMove(Position start, Position end){
        return moveValidationStrategy.isAvailableMove(start, end, blot);
    }

    public boolean isCreator() {
        return creator;
    }


}
