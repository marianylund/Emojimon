package com.progark.emojimon.model.factories;

import com.progark.emojimon.model.BasicMoveValidationStrategy;
import com.progark.emojimon.model.strategyPattern.MoveValidationStrategy;

//implements parameterized factory method for MoveValidationStrategy
public class MoveValidationStrategyFactory {

    public enum MoveValStrat {
        BASIC,
        MOVEBACKWARDS
    }

    // Create a concrete strategy for validating a move
    public static MoveValidationStrategy getMoveValidationStrategy(MoveValStrat moveValidation){
        if (moveValidation == null){
            return null;
        }
        if (moveValidation == MoveValStrat.BASIC){
            return new BasicMoveValidationStrategy();
        }
        return null;
    }
}
