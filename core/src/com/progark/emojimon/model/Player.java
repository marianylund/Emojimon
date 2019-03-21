package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.Die;
import com.progark.emojimon.model.interfaces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private List<Piece> boardPieces;
    private List<Piece> clearedPieces;
    private Position bar;
    private int homeAreaStartIndex;
    private int homeAreaEndIndex;
    private boolean moveClockwise;

    public Player(int homeAreaStartIndex, int homeAreaEndIndex, boolean moveClockwise){
        this.homeAreaStartIndex = homeAreaStartIndex;
        this.homeAreaEndIndex = homeAreaEndIndex;
        this.moveClockwise = moveClockwise;

        boardPieces = new ArrayList<Piece>();
        clearedPieces = new ArrayList<Piece>();
        bar = new Position();
    }

    //Get all available moves
    //(currently only checking individual die, not combinations)
    public List<Move> getAvailableMoves(List<Die> dice, List<Position> positions) {
        List<Move> moves = new ArrayList<Move>();

        //find all possible moves for player given die values in dice
        for(int positionIndex = 0; positionIndex < positions.size(); positionIndex++){
            if(positions.get(positionIndex).getOwner() == this){
                //check for possible moves
                for(int diceIndex = 0; diceIndex < dice.size(); diceIndex++){
                    int diceValue = dice.get(diceIndex).GetValue();
                    int endPosition = moveClockwise ? (positionIndex + diceValue) : (positionIndex - diceValue);
                    if(positions.get(endPosition).getOwner() == this){
                        moves.add(new Move(positionIndex, endPosition));
                    }
                    else{
                        //only add if number of enemy pieces on position does not exceed limit
                        if(positions.get(endPosition).getNumberOfPieces() < 1){
                            moves.add(new Move(positionIndex, endPosition));
                        }
                    }
                }
            }
        }

        return moves;
    }

    //returns whether player has cleared all of their pieces
    public boolean isDone() {
        return (boardPieces.size() == 0);
    }

    //returns whether player is in a position to start clearing pieces
    public boolean canClear() {
        //iterate over all pieces and check if their position is within range[homeAreaStartIndex, homeAreaEndIndex]
        for(int i = 0; i < boardPieces.size(); i++){
            int piecePositionIndex = boardPieces.get(i).getPosition().getPositionIndex();
            if(piecePositionIndex < homeAreaStartIndex || piecePositionIndex > homeAreaEndIndex){
                //piece is outside of home area, player can not clear
                return false;
            }
        }
        //all pieces are within home area, player can clear
        return true;
    }

    public Position getBar(){
        return bar;
    }

    public void addToBoardPieces(Piece p){
        this.boardPieces.add(p);
    }
}
