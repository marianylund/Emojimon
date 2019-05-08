package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.scenes.scene2d.Event;

public class CellClickEvent extends Event {
    int index;

    public CellClickEvent(int index){
        this.index = index;
    }

}
