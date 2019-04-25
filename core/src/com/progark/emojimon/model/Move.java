package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.Die;

//Custom type representing a piece move from startPosition to endPosition
public class Move {
    public int startPosition;
    public int endPosition;
    public Die die; //die used to perform move

    public Move(int startPosition, int endPosition, Die die){
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.die = die;
    }
}
