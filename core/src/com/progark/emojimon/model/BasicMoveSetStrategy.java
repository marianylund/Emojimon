package com.progark.emojimon.model;

import com.progark.emojimon.model.strategyPattern.MoveSetStrategy;

import java.util.List;
//
public class BasicMoveSetStrategy implements MoveSetStrategy {

    private int blot; // piece/s that can be thrown out to bar

    public BasicMoveSetStrategy(int blot){
        this.blot = blot;
    }

    @Override
    public boolean doMove(Move move, List<Position> boardPositions, Position bar, Player inBar) {
        Position startPosition = boardPositions.get(move.startPosition);
        Position endPosition = boardPositions.get(move.endPosition);

        // move piece as long as position is not owned by another player
        if(endPosition.getOwner() != startPosition.getOwner()){

            // // throw opposite players' pieces to bar, if there's blot piece/s
            if (endPosition.getPieceCount() == blot){
                bar.addPieces(blot);
                inBar = endPosition.getOwner();
                endPosition.removePieces(blot);
                return true;
            }
        return false;
        }
        startPosition.addPieces(1);
        startPosition.removePieces(1);
        endPosition.addPieces(1);
        return true;
    }

}
