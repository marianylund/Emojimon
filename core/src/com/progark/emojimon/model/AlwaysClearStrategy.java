package com.progark.emojimon.model;

import com.progark.emojimon.model.strategyPattern.CanClearStrategy;

import java.util.List;

//While using this strategy, players can always clear pieces from the board
public class AlwaysClearStrategy implements CanClearStrategy {
    @Override
    public boolean canClear(Player player, List<Position> boardPositions) {
        return true;
    }
}
