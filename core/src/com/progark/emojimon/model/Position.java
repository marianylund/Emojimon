package com.progark.emojimon.model;

public class Position {
    private int pieceCount;
    private int positionIndex;
    private Player owner;

    public Position(){
        pieceCount = 0;
    }

    public Position(int positionIndex){
        this.positionIndex = positionIndex;
    }

    public int getPositionIndex(){
        return positionIndex;
    }


    public Player getOwner(){
        return owner;
    }

    public void setOwner(Player owner){
        this.owner = owner;
    }

    public int getPieceCount(){
        return pieceCount;
    }

    public void addPieces(int numberOfPieces){
        pieceCount += numberOfPieces;
    }

    public void removePieces(int numberOfPieces){
        pieceCount -= numberOfPieces;
    }
}
