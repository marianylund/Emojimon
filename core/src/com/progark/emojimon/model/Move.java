package com.progark.emojimon.model;

//Custom type representing a piece move from startPosition to endPosition
public class Move {
    public int startPosition;
    public int endPosition;


    public Move(int startPosition, int endPosition){
        this.startPosition = startPosition;
        this.endPosition = endPosition;
    }
}
