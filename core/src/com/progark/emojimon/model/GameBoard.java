package com.progark.emojimon.model;

import com.progark.emojimon.model.interfaces.DiceRule;
import com.progark.emojimon.model.interfaces.Die;
import com.progark.emojimon.model.interfaces.Piece;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {
    private Player blackPlayer;
    private Player whitePlayer;
    private List<Die> dice;
    private DiceRule diceRule;
    //includes all board positions indexed from bottom right to top right
    private List<Position> boardPositions;
    private int boardSize;

    //constructor
    //(currently creating standard gameboard)
    public GameBoard(int boardSize){
        //create players
        Player whitePlayer = new Player(18, 23, true);
        Player blackPlayer = new Player(0, 5, false);

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
        //whiteplayer
        int whitePlayerPieceCount = 0;
        //place white pieces according to standard piece placements
        for(int i = 0; i < 15; i++){
            Piece p = new StandardPiece(whitePlayer);
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
            whitePlayer.addToBoardPieces(p);
        }

        //place black pieces according to standard piece placements
        for(int i = 0; i < 15; i++){
            Piece p = new StandardPiece(blackPlayer);
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

    public Position getWhitePlayerBar(){
        return whitePlayer.getBar();
    }

    public Position getBlackPlayerBar(){
        return blackPlayer.getBar();
    }

    public List<Move> getBlackPlayerMoves(){
        return blackPlayer.getAvailableMoves(dice, boardPositions);
    }

    public List<Move> getWhitePlayerMoves(){
        return whitePlayer.getAvailableMoves(dice, boardPositions);
    }
}
