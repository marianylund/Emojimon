package com.progark.emojimon.model;

import com.progark.emojimon.model.strategyPattern.MoveSetStrategy;

import java.util.List;
//
public class BasicMoveSetStrategy implements MoveSetStrategy {

    @Override
    public void calculateMove(Move move, List<Position> boardPositions, Position bar, Player inBar) {
        Position startPosition = boardPositions.get(move.startPosition);
        Position endPosition = boardPositions.get(move.endPosition);

        //check if endPosition is owned by other player
        if(endPosition.getOwner() != startPosition.getOwner()){

            //move pieces on endposition to bar, if there's one piece
            if (endPosition.getPieceCount() == 1){
                bar.addPieces(1);
                inBar = endPosition.getOwner();
                endPosition.removePieces(1);
            }

        }
        startPosition.addPieces(1);
        startPosition.removePieces(1);
        endPosition.addPieces(1);
    }

}
