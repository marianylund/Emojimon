package com.progark.emojimon.model.factories;

import com.progark.emojimon.model.BasicMoveSetStrategy;
import com.progark.emojimon.model.strategyPattern.MoveSetStrategy;

//implements parameterized factory method for MoveSetStrategy
public class MoveSetStrategyFactory {

    //Create a concrete strategy for calculating piece movement
    public static MoveSetStrategy GetMoveSet(String moveSet, int blot){
        if (moveSet == null){
            return null;
        }
        if (moveSet.equalsIgnoreCase("BASIC")){
            return new BasicMoveSetStrategy(blot);
        }
        return null;
    }

}
