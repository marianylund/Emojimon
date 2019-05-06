package com.progark.emojimon.model.factories;

import com.progark.emojimon.model.BasicMoveSetStrategy;
import com.progark.emojimon.model.strategyPattern.MoveSetStrategy;

//implements parameterized factory method for MoveSetStrategy
public class MoveSetStrategyFactory {

    public enum MoveSetStrat {
        BASIC
    }

    //Create a concrete strategy for calculating piece movement
    public static MoveSetStrategy GetMoveSet(MoveSetStrat moveSet, int blot){
        if (moveSet == null){
            return null;
        }
        if (moveSet == MoveSetStrat.BASIC){
            return new BasicMoveSetStrategy(blot);
        }
        return null;
    }

}
