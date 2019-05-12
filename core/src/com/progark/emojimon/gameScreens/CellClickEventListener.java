package com.progark.emojimon.gameScreens;

import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;

public class CellClickEventListener implements EventListener {


    @Override
    public boolean handle(Event event) {
        if(event instanceof CellClickEvent){
            event.handle();
            OnClick((CellClickEvent)event, ((CellClickEvent) event).index);
            return true;
        }
        else{
            return false;
        }
    }

    public void OnClick(CellClickEvent event, int index){

    }
}
