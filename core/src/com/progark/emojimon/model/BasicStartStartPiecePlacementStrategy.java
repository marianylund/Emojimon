package com.progark.emojimon.model;

import com.progark.emojimon.model.strategyPattern.StartPiecePlacementStrategy;

import java.util.Arrays;
import java.util.List;

// basic strategy for start positions piece placements. Default is:
// [0, , , ,1, || ,1, , , , ,0]
// [1, , , ,0, || ,0, , , , , 1]

//Eg. standard boardSize 24 and 15 pieces per player:
//player0, positions: [24, 13, 8, 6]
//player1, positions: [1, 12, 17, 19]

public class BasicStartStartPiecePlacementStrategy implements StartPiecePlacementStrategy {

    @Override
    public void placeStartPieces(int boardSize, int piecesPerPlayer, List<Position> boardPositions, Player p0, Player p1, int pieces) {

        // lists of standard positions for pieces
        List<Integer> player0PiecesPositions = Arrays.asList(boardSize, (boardSize/2)+1, (boardSize/4)+2, boardSize/4);
        List<Integer> player1PiecesPositions = Arrays.asList(1, boardSize/2, (boardSize/4)*3-1, (boardSize/4)*3 + 1);

        //place white pieces according to standard piece placements
        placePiecesOnBoard(player0PiecesPositions, boardPositions, pieces, p0);

        //place black pieces according to standard piece placements
        placePiecesOnBoard(player1PiecesPositions, boardPositions, pieces, p1);
    }

    private void placePiecesOnBoard(List<Integer> playerPiecesPos, List<Position> boardPositions, int pieces, Player player){
        List<Integer> numberOfPieces = Arrays.asList(2,5,3,5); // default amount of pieces
        for (int i = 0; i < 4; i++){
            boardPositions.get(playerPiecesPos.get(i)).addPieces(numberOfPieces.get(i));
            boardPositions.get(playerPiecesPos.get(i)).setOwner(player);
        }

        // if more than 15 pieces per player, spread rest of the pieces to placements accordingly
        if (pieces > 15) {
            int piecesLeft = pieces - 15;
            int i = 0;
            while (piecesLeft != 0) {
                boardPositions.get(playerPiecesPos.get(i)).addPieces(1);
                --piecesLeft;
                i++;
                if (i == 4){
                    i = 0; // reset
                }
            }

        }
    }

}
