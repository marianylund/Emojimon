package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.Die;

import java.util.ArrayList;
import java.util.List;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public class Player {

    private int homeAreaStartIndex;
    private int homeAreaEndIndex;
    private boolean moveClockwise;

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
            if(positions.get(positionIndex).getOwner() == this){
                //check for possible moves
                for(int diceIndex = 0; diceIndex < dice.size(); diceIndex++){
                    int diceValue = dice.get(diceIndex).GetValue();
                    int endPosition = moveClockwise ? (positionIndex + diceValue) : (positionIndex - diceValue);
                    if(positions.get(endPosition).getOwner() == this){
                        moves.add(new Move(positionIndex, endPosition));
                    }
                    else{
                        //only add if number of enemy pieces on position does not exceed limit
                        if(positions.get(endPosition).getPieceCount() < 1){
                            moves.add(new Move(positionIndex, endPosition));
                        }
                    }
                }
            }
        }

        return moves;
    }

    //returns whether player has cleared all of their pieces
    public boolean isDone() {
        throw new NotImplementedException();
    }

    //returns whether player is in a position to start clearing pieces
    public boolean canClear(List<Position> boardPositions, Position bar) {
        throw new NotImplementedException();
    }

}
