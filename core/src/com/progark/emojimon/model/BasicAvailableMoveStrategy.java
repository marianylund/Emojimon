package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.Die;
import com.progark.emojimon.model.strategyPattern.MoveValidationStrategy;

import java.util.ArrayList;
import java.util.List;

public class BasicAvailableMoveStrategy implements MoveValidationStrategy {
    @Override
    //Validates move based on standard backgammon movement rules
    //Valid if endposition is free, endposition is owned by moving player, or endposition contains 1 enemy piece
    public boolean isAvailableMove(Position startPosition, Position endPosition){
        boolean valid = false;
        if(endPosition.getPieceCount() == 0){
            valid = true;
        }
        else if(endPosition.getOwner() == startPosition.getOwner()){
            valid = true;
        }
        else if(endPosition.getPieceCount() == 1){
            valid = true;
        }
        return valid;
    }
}
