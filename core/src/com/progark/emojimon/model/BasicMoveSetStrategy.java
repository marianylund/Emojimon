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
    public void calculateMove(Move move, List<Position> boardPositions) {
        Position startPosition = boardPositions.get(move.startPosition);
        Position endPosition = boardPositions.get(move.endPosition);
        Position bar = boardPositions.get(0);

        //check if endPosition is owned by other player
        if(endPosition.getOwner() != startPosition.getOwner()){

            //move pieces on endposition to bar, if there's blot piece/s
            if (endPosition.getPieceCount() == blot && (bar.getOwner().equals(null) || bar.getOwner().equals(endPosition.getOwner()))){
                if (bar.getOwner().equals(null)){
                    bar.setOwner(endPosition.getOwner());
                }
                bar.addPieces(blot);
                endPosition.removePieces(blot);
            }

        }
        startPosition.addPieces(1);
        startPosition.removePieces(1);
        endPosition.addPieces(1);
        // if the player has cleared their bar, set owner to null
        if (bar.getPieceCount() == 0 && bar.getOwner().equals(endPosition.getOwner())){
            bar.setOwner(null);
        }
    }

}
