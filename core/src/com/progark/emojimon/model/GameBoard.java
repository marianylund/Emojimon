package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.DiceRule;
import com.progark.emojimon.model.interfaces.Die;
import com.progark.emojimon.model.interfaces.Piece;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private Player player0;
    private Player player1;
    private List<Die> dice;
    private DiceRule diceRule;
    //includes all board positions indexed from bottom right to top right
    private List<Position> boardPositions;
    private int boardSize;

    //constructor
    //(currently creating standard gameboard)
    public GameBoard(int boardSize){
        //create players
        Player player1 = new Player(18, 23, true);
        Player player0 = new Player(0, 5, false);

        this.boardSize = boardSize;
        //create all positions
        for(int i = 0; i < boardSize; i++){
            Position p = new Position(i);
            boardPositions.add(p);
        }

        //create dice
        dice = new ArrayList<Die>();
        Die d1 = new SixSidedDie();
        Die d2 = new SixSidedDie();
        dice.add(d1);
        dice.add(d2);

        //create pieces
        //Player0
        //place white pieces according to standard piece placements
        for(int i = 0; i < 15; i++){
            Piece p = new StandardPiece(player1);
            if(i < 2){
                boardPositions.get(0).placePiece(p);
            }
            else if(i < 7){
                boardPositions.get(11).placePiece(p);
            }
            else if(i < 10){
                boardPositions.get(16).placePiece(p);
            }
            else{
                boardPositions.get(18).placePiece(p);
            }
            player1.addToBoardPieces(p);
        }

        //place pieces of player 0 according to standard piece placements
        for(int i = 0; i < 15; i++){
            Piece p = new StandardPiece(player0);
            if(i < 2){
                boardPositions.get(23).placePiece(p);
            }
            else if(i < 7){
                boardPositions.get(12).placePiece(p);
            }
            else if(i < 10){
                boardPositions.get(7).placePiece(p);
            }
            else{
                boardPositions.get(5).placePiece(p);
            }
        }

    }

    public void movePiece(Move move){
        Position startPosition = boardPositions.get(move.startPosition);
        Position endPosition = boardPositions.get(move.endPosition);
        Piece p = startPosition.pop();

        //check if endPosition is owned by other player
        if(endPosition.getOwner() != p.getOwner()){
            //move pieces on endposition to bar
            while(endPosition.getNumberOfPieces() > 0){
                endPosition.getOwner().getBar().placePiece(endPosition.pop());
            }
        }

        boardPositions.get(move.endPosition).placePiece(p);
    }

    public void rollDice(){
        for(int i = 0; i < dice.size(); i++){
            dice.get(i).Roll();
        }
    }

    public List<Position> getBoardPositions(){
        return boardPositions;
    }

    public List<Die> getDice(){
        return dice;
    }

    public Position getPlayer1Bar(){
        return player1.getBar();
    }

    public Position getPlayer0Bar(){
        return player0.getBar();
    }

    public List<Move> getPlayer0Moves(){
        return player0.getAvailableMoves(dice, boardPositions);
    }

    public List<Move> getPlayer1Moves(){
        return player1.getAvailableMoves(dice, boardPositions);
    }
}
