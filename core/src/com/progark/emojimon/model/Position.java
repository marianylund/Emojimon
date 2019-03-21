package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.Piece;

import java.util.Stack;

public class Position {
    private Stack<Piece> pieces;
    private int positionIndex;

    public Position(){pieces = new Stack<Piece>();}
    public Position(int positionIndex){
        this.positionIndex = positionIndex;
        pieces = new Stack<Piece>();
    }
    public Position(Stack<Piece> pieces){
        this.pieces = pieces;
    }

    public Piece pop(){
        return pieces.pop();
    }

    public Piece peek(){
        return pieces.peek();
    }

    public void placePiece(Piece piece){
        pieces.push(piece);
        piece.setPosition(this);
    }

    public int getPositionIndex(){
        return positionIndex;
    }

    public int getNumberOfPieces(){
        return pieces.size();
    }

    public Player getOwner(){
        if(getNumberOfPieces() > 0){
            return peek().getOwner();
        }
        else{
            return null;
        }
    }
}
