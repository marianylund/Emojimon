package com.progark.emojimon.model.factories;

import com.progark.emojimon.model.BasicStartStartPiecePlacementStrategy;
import com.progark.emojimon.model.strategyPattern.StartPiecePlacementStrategy;

// implements factory method for StartPiecePlacementStrategy

public class StartPiecePlacementStrategyFactory {
    // Create a concrete strategy for placing the start positions of the pieces
    public static StartPiecePlacementStrategy getPiecePlacementStrategy(String startPiecePlacementValidation){
        if (startPiecePlacementValidation == null){
            return null;
        }
        if (startPiecePlacementValidation.equalsIgnoreCase("BASIC")){
            return new BasicStartStartPiecePlacementStrategy();
        }
        return null;
    }
}
