package com.progark.emojimon.model.factories;

import com.progark.emojimon.model.AlwaysClearStrategy;
import com.progark.emojimon.model.BasicCanClearStrategy;
import com.progark.emojimon.model.strategyPattern.CanClearStrategy;

//implements parameterized factory method for CanClearStrategy
public class CanClearStrategyFactory {

    public enum CanClearStrat {
        BASIC,
        ALWAYS
    }

    // Create a concrete strategy for bearing off (clearing pieces)
    public static CanClearStrategy getCanClearStrategy(CanClearStrat canClear){
        if (canClear == null){
            return null;
        }
        if (canClear == CanClearStrat.BASIC){
            return new BasicCanClearStrategy();
        }
        else if(canClear == CanClearStrat.ALWAYS){
            return new AlwaysClearStrategy();
        }
        return null;
    }
}
