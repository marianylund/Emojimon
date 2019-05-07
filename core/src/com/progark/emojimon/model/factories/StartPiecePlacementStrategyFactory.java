package com.progark.emojimon.model.factories;

import com.progark.emojimon.model.BasicStartPiecePlacementStrategy;
import com.progark.emojimon.model.strategyPattern.StartPiecePlacementStrategy;

// implements factory method for StartPiecePlacementStrategy

public class StartPiecePlacementStrategyFactory {

    public enum PiecePlacementStrat{
        BASIC
    }

    // Create a concrete strategy for placing the start positions of the pieces
    public static StartPiecePlacementStrategy getPiecePlacementStrategy(PiecePlacementStrat piecePlacementStrat){
        if (piecePlacementStrat == null){
            return null;
        }
        if (piecePlacementStrat == PiecePlacementStrat.BASIC){
            return new BasicStartPiecePlacementStrategy();
        }
        return null;
    }
}
