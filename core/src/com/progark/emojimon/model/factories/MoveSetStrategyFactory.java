package com.progark.emojimon.model.factories;

import com.progark.emojimon.model.BasicMoveSetStrategy;
import com.progark.emojimon.model.strategyPattern.MoveSetStrategy;

public class MoveSetStrategyFactory {

    //Create a concrete strategy for calculating piece movement
    public MoveSetStrategy GetMoveSet(String moveSet, int blot){
        if (moveSet == null){
            return null;
        }
        if (moveSet.equalsIgnoreCase("BasicMoveSet")){
            return new BasicMoveSetStrategy(blot);
        }
        return null;
    }

}
