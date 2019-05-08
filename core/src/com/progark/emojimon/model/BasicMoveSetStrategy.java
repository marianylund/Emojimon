package com.progark.emojimon.model;

import com.progark.emojimon.model.strategyPattern.MoveSetStrategy;

import java.util.List;
//
public class BasicMoveSetStrategy implements MoveSetStrategy {

    private int blot; // piece/s that can be thrown out to bar

    public BasicMoveSetStrategy(int blot){
        this.blot = blot;
    }

    // perform piece movement from start position to end position
    @Override
    public boolean doMove(Move move, List<Position> boardPositions) {
        Position startPosition = boardPositions.get(move.startPosition);
        Position endPosition = boardPositions.get(move.endPosition);
        Position bar = boardPositions.get(0);


        // check if players piece can be moved
        if (startPosition.getOwner().isAvailableMove(startPosition, endPosition)){

            // throw opposite players piece
            if(endPosition.getOwner() != null){
                if (endPosition.getOwner() != startPosition.getOwner()){
                    //move pieces from endposition to bar
                    bar.addPieces(blot);
                    endPosition.removePieces(blot);

                    // set bar owner
                    if (!bar.getOwner().equals(endPosition.getOwner())){
                        bar.setOwner(endPosition.getOwner());
                    }
                }
            }

            startPosition.removePieces(1);
            endPosition.addPieces(1);

            //update owners
            System.out.println("Changing owner from " + endPosition.getOwner() + " to " + startPosition.getOwner());
            endPosition.setOwner(startPosition.getOwner());
            if(startPosition.getPieceCount() == 0){
                startPosition.setOwner(null);
            }

            if(bar.getOwner() != null){
                // if the player has cleared their bar, set owner to null
                if (bar.getPieceCount() == 0 && bar.getOwner().equals(endPosition.getOwner())) {
                    bar.setOwner(null);
                }
            }


            return true;
        }

        return false;
    }
}
