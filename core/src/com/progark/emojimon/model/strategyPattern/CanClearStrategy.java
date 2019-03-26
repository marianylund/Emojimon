package com.progark.emojimon.model.strategyPattern;

import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;

import java.util.List;

public interface CanClearStrategy {

    boolean canClear(Player player, List<Position> boardPositions, Position bar);
}
