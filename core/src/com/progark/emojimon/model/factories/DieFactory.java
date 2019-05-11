package com.progark.emojimon.model.factories;

import com.progark.emojimon.model.SixSidedDie;
import com.progark.emojimon.model.TenSidedDie;
import com.progark.emojimon.model.interfaces.Die;

//implements parameterized factory method for Die
public class DieFactory {

    public enum DieType{
        SIXSIDED,
        TENSIDED
    }

    //Create a concrete Die based on parameters given
    public static Die CreateDie(DieType dieType){
        switch(dieType){
            case SIXSIDED:
                return new SixSidedDie();
            case TENSIDED:
                return new TenSidedDie();
            default:
                System.out.println(String.format("No die matching type: " + dieType));
                break;
        }
        return null;
    }
}