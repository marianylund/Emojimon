package com.progark.emojimon.model.strategyPattern;

import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;

import java.util.List;

public interface StartPiecePlacementStrategy {
    void placeStartPieces(int boardSize, int piecesPerPlayer, List<Position> boardPositions, Player p0, Player p1, int pieces);
}
