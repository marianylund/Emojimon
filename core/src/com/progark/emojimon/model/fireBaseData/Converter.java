package com.progark.emojimon.model.fireBaseData;

import com.progark.emojimon.model.GameBoard;
import com.progark.emojimon.model.Move;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;
import com.progark.emojimon.model.interfaces.Die;

import java.util.ArrayList;
import java.util.List;

// Converts data from and to firebase supported format and mvc
public class Converter {

    //region from MVC to FireBase
    public static List<Integer> fromDiceToList(List<Die> die){
        List<Integer> diceList = new ArrayList<Integer>();
        for (Die d :die) {
            diceList.add(d.getValue());
        }
        return diceList;
    }

    public static List<List<Integer>> fromBoardPositionsToList(List<Position> boardPositions){
        List<List<Integer>> gameBoard = new ArrayList<List<Integer>>();
        for (Position p: boardPositions)
        {
            List<Integer> pos = new ArrayList<Integer>();
            pos.add(p.getPieceCount());
            if(p.getOwner() != null){
                pos.add(fromPlayerToPlayerNumber(p.getOwner()));
            }
            else{
                //use -1 to denote lack of owner?
                pos.add(-1);
            }
            gameBoard.add(p.getPositionIndex(), pos);
        }
        return gameBoard;
    }

    public static int fromPlayerToPlayerNumber(Player p){
        return p.isCreator() ? 0 : 1;
    }

    public static List<List<Integer>> fromMovesToList(List<Move> moves){
        List<List<Integer>> movesList = new ArrayList<List<Integer>>();
        for (Move m: moves)
        {
            List<Integer> fromTo = new ArrayList<Integer>();
            fromTo.add(m.startPosition);
            fromTo.add(m.endPosition);
            movesList.add(fromTo);
        }
        return movesList;
    }

    //endregion

    //region from FireBase to MVC
    public static Player fromPlayerNumberToPlayer(int n, GameBoard gb){ //Is this function necessary?
        if(n==0){
            return gb.getPlayer0();
        } else if(n==1){
            return gb.getPlayer1();
        }else{
            throw new IllegalArgumentException("There is no player with " + n + " number");
        }
    }

    public static List<Move> fromListToMoves(List<List<Integer>> moveList){
        List<Move> moves = new ArrayList<Move>();
        for (List<Integer> l: moveList) {
            Move m = new Move(l.get(0),l.get(1));
            moves.add(m);
        }
        return moves;
    }
    //endregion

}
