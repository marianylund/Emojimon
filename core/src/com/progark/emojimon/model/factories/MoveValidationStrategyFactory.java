package com.progark.emojimon.model.factories;

import com.progark.emojimon.model.BasicAvailableMoveStrategy;
import com.progark.emojimon.model.strategyPattern.MoveValidationStrategy;

public class MoveValidationStrategyFactory {

    // Create a concrete strategy for validating a move
    public MoveValidationStrategy CreateMoveValidationStrategy(String moveValidation){
        if (moveValidation == null){
            return null;
        }
        if (moveValidation.equalsIgnoreCase("BASIC")){
            return new BasicAvailableMoveStrategy();
        }
        return null;
    }
}
