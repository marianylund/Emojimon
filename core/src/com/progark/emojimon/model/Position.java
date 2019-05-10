package com.progark.emojimon.model;


import java.util.Observable;
import java.util.Observer;

//Represents board position
//Keeps track of number of pieces and owner
//Notifies observers of changes to state
public class Position extends Observable {
    private int pieceCount;
    private int positionIndex;
    private Player owner;

    public Position(){
        this(0);
    }

    public Position(int positionIndex){
        this.positionIndex = positionIndex;
        //notify listeners
        onChanges();
    }

    public int getPositionIndex(){
        return positionIndex;
    }


    public Player getOwner(){
        return owner;
    }

    public void setOwner(Player owner){
        this.owner = owner;
        onChanges();
    }

    public int getPieceCount(){
        return pieceCount;
    }

    public void setPieceCount(int pieceCount){
        this.pieceCount = pieceCount;
        onChanges();
    }

    public void addPieces(int numberOfPieces){
        setPieceCount(pieceCount + numberOfPieces);
    }

    public void removePieces(int numberOfPieces){
        setPieceCount(pieceCount - numberOfPieces);
    }


    public void onChanges(){
        setChanged();
        notifyObservers(this);
    }

    //adds observer and notifies of changes
    public void addNewObserver(Observer observer){
        addObserver(observer);
        onChanges();
    }
}
