package com.progark.emojimon.model;

import com.progark.emojimon.model.factories.DieFactory;
import com.progark.emojimon.model.interfaces.Die;

import java.util.ArrayList;
import java.util.List;

public class Dice {
    private List<Die> dieList;
    private int baseNumberOfDice;
    private int diceMultiplier; //multiplier to be applied when throwing all equals
    private DieFactory.DieType dieSides;

    public Dice(int baseNumberOfDice, int diceMultiplier, DieFactory.DieType dieSides){
        this.baseNumberOfDice = baseNumberOfDice;
        this.diceMultiplier = diceMultiplier;
        this.dieSides = dieSides;

        dieList = new ArrayList<Die>();
        for(int i = 0; i < baseNumberOfDice; i++){
            Die d = DieFactory.CreateDie(dieSides);
            dieList.add(d);
        }
    }

    //multiplies number of dieList
    //sets value of all new dieList to dieValue
    public void multiplyDice(int dieValue){
        int newNumberOfDice = baseNumberOfDice * diceMultiplier;
        for(int i = baseNumberOfDice; i < newNumberOfDice; i++){
            Die d = DieFactory.CreateDie(dieSides);
            d.setValue(dieValue);
            dieList.add(d);
        }
    }

    //reverts dieList list to only contain base number of die
    public void revertDice(){
        if(dieList.size() > baseNumberOfDice){
            //clear all extra dieList
            dieList.subList(baseNumberOfDice, dieList.size()).clear();
        }
    }

    public List<Die> getDieList(){
        return dieList;
    }

    public void rollDice(){
        //revert dice include base number of die
        revertDice();
        
        boolean equalDice = true;
        int lastDieValue = 0;
        for(int i = 0; i < dieList.size(); i++){
            Die die = dieList.get(i);
            die.roll();

            if(i == 0) lastDieValue = die.getValue();
            else{
                //keep track of whether all die have the same value
                if(equalDice){
                    if(die.getValue() != lastDieValue){
                        equalDice = false;
                    }
                }
                lastDieValue = die.getValue();
            }

            die.setUsed(false);
        }
        //multiply dice if all dice were equal
        if(equalDice){
            multiplyDice(lastDieValue);
        }
    }

}
