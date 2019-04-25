package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.Die;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class SixSidedDie implements Die {
    private int currentValue;
    private int minValue;
    private int maxValue;

    public SixSidedDie(){
        minValue = 1;
        maxValue = 6;
        currentValue = maxValue;
    }

    @Override
    public void roll() {
        Random r = new Random();
        //set currentValue to random number in range[minValue, maxValue]
        currentValue = r.nextInt((maxValue-minValue) + 1) + minValue;
    }

    @Override
    public int getValue() {
        return currentValue;
    }

    @Override
    public void setValue(int value){ currentValue = value;}

    @Override
    public void setUsed(boolean used) {

    }

    @Override
    public boolean getUsed() {
        return false;
    }
}
