package com.progark.emojimon.model.strategyPattern;

import com.progark.emojimon.model.Move;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;

import java.util.List;

public interface MoveSetStrategy {
    boolean doMove(Move move, List<Position> boardPositions, Position bar, Player inBar);
}
