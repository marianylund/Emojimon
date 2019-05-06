package com.progark.emojimon.model.strategyPattern;

import com.progark.emojimon.model.Move;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;
import com.progark.emojimon.model.interfaces.Die;

import java.util.List;

public interface MoveValidationStrategy {
    boolean isAvailableMove(Position startPosition, Position endPosition);
}
