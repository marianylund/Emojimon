package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.Die;
import com.progark.emojimon.model.strategyPattern.MoveValidationStrategy;

import java.util.ArrayList;
import java.util.List;

public class BasicMoveValidationStrategy implements MoveValidationStrategy {
    @Override
    //Validates move based on standard backgammon movement rules
    //Valid if endposition is free, endposition is owned by moving player, or endposition contains 1 enemy piece
    // blot is how many of the opposing pieces in an endposition can be thrown of to bar
    public boolean isAvailableMove(Position startPosition, Position endPosition, int blot){
        boolean valid = false;
        if(endPosition.getPieceCount() == 0){
            valid = true;
        }
        else if(endPosition.getOwner() == startPosition.getOwner()){
            valid = true;
        }
        else if(endPosition.getPieceCount() == blot){
            valid = true;
        }
        return valid;
    }
}