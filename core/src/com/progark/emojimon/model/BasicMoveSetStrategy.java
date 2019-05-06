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
            System.out.println(endPosition.getOwner());
            System.out.print(bar.getOwner());
            if (!endPosition.getOwner().equals(startPosition.getOwner())){
                bar.addPieces(blot);
                endPosition.removePieces(blot);

                // set bar owner
                if (!bar.getOwner().equals(endPosition.getOwner())){
                    bar.setOwner(endPosition.getOwner());
                }
            }

            startPosition.addPieces(1);
            startPosition.removePieces(1);
            endPosition.addPieces(1);

            //update owner of endposition if necessary
            if(endPosition.getOwner() != startPosition.getOwner()){
                endPosition.setOwner(startPosition.getOwner());
            }

            // if the player has cleared their bar, set owner to null
            if (bar.getPieceCount() == 0 && bar.getOwner().equals(endPosition.getOwner())) {
                bar.setOwner(null);
            }

            return true;
        }

        return false;
    }
}
