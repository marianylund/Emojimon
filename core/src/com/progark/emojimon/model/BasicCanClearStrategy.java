package com.progark.emojimon.model;

import com.progark.emojimon.model.strategyPattern.CanClearStrategy;

import java.util.List;

public class BasicCanClearStrategy implements CanClearStrategy {

    @Override
    //Standard rule: check if all player's pieces are within the goal area
    public boolean canClear(Player player, List<Position> boardPositions) {
        if (boardPositions.get(0).getOwner() == player){
            return false; // player has piece in bar
        }

        for(int i = 0; i < boardPositions.size(); i++){
            if(boardPositions.get(i).getOwner() == player){
                int positionIndex = boardPositions.get(i).getPositionIndex();
                if(positionIndex < player.getHomeAreaStartIndex() || positionIndex > player.getHomeAreaEndIndex()){
                    return false;
                }
            }
        }

        return true;
    }
}
