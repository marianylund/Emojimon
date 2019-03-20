package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.Piece;

public class StandardPiece implements Piece {
    private Position position;
    private Player owner;

    public StandardPiece(Player owner){
        this.owner = owner;
    }

    public void placeOnBar(){

    }

    @Override
    public Position getPosition(){
        return position;
    }

    public void setPosition(Position position){
        this.position = position;
    }

    public Player getOwner(){
        return owner;
    }
}
