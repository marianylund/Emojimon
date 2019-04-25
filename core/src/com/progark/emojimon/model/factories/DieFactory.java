package com.progark.emojimon.model.factories;

import com.progark.emojimon.model.SixSidedDie;
import com.progark.emojimon.model.interfaces.Die;

//implements parameterized factory method for Die
public class DieFactory {

    //Create a concrete Die based on parameters given
    public static Die CreateDie(int sides){
        switch(sides){
            case 6:
                return new SixSidedDie();
            default:
                System.out.println(String.format("No die matching parameter sides: %d", sides));
                break;
        }
        return null;
    }
}
