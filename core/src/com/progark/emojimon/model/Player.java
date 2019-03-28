package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.Die;
import com.progark.emojimon.model.strategyPattern.CanClearStrategy;
import com.progark.emojimon.model.strategyPattern.MoveValidationStrategy;

import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Player {

    private int homeAreaStartIndex;
    private int homeAreaEndIndex;
    private boolean moveClockwise;
    private MoveValidationStrategy moveValidationStrategy;
    private CanClearStrategy canClearStrategy;

    public Player(int homeAreaStartIndex, int homeAreaEndIndex, boolean moveClockwise){
        this.homeAreaStartIndex = homeAreaStartIndex;
        this.homeAreaEndIndex = homeAreaEndIndex;
        this.moveClockwise = moveClockwise;
    }

    //Get all available moves
    //(currently only checking individual die, not combinations)
    public List<Move> getAvailableMoves(List<Die> dice, List<Position> positions, Position bar) {
        List<Move> moves = new ArrayList<Move>();

        //find all possible moves for player given die values in dice
        for(int positionIndex = 0; positionIndex < positions.size(); positionIndex++){
            Position startPosition = positions.get(positionIndex);
            if(startPosition.getOwner() == this){
                //check for possible moves
                for(int diceIndex = 0; diceIndex < dice.size(); diceIndex++){
                    int diceValue = dice.get(diceIndex).getValue();
                    int endPositionIndex = moveClockwise ? (positionIndex + diceValue) : (positionIndex - diceValue);
                    Position endPosition = positions.get(endPositionIndex);

                    //apply move validation strategy to check if move is valid
                    if(moveValidationStrategy.isAvailableMove(startPosition, endPosition)){
                        moves.add(new Move(positionIndex, endPositionIndex));
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

    //returns whether player has cleared all of their pieces
    public boolean isDone() {
        throw new NotImplementedException();
    }

    //returns whether player is in a position to start clearing pieces
    public boolean canClear(List<Position> boardPositions, Position bar) {
        return canClearStrategy.canClear(this, boardPositions, bar);
    }


}
