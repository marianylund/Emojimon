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
    public boolean doMove(Move move, List<Position> boardPositions) {
        Position startPosition = boardPositions.get(move.startPosition);
        Position endPosition = boardPositions.get(move.endPosition);
        Position bar = boardPositions.get(0);

        // move piece as long as position is not owned by another player
        if(endPosition.getOwner() != startPosition.getOwner()){

            // // throw opposite players' pieces to bar, if there's blot piece/s
            if (endPosition.getPieceCount() == blot && (bar.getOwner().equals(null) || bar.getOwner().equals(endPosition.getOwner()))){
                if (bar.getOwner().equals(null)){
                bar.addPieces(blot);
                endPosition.removePieces(blot);
                return true;
            }
        return false;
        }
        startPosition.addPieces(1);
        startPosition.removePieces(1);
        endPosition.addPieces(1);

        // if the player has cleared their bar, set owner to null
        if (bar.getPieceCount() == 0 && bar.getOwner().equals(endPosition.getOwner())){
            bar.setOwner(null);
        }
        return true;
    }

}
