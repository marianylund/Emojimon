package com.progark.emojimon.controller;

import com.progark.emojimon.model.GameBoard;
import com.progark.emojimon.model.Move;
import com.progark.emojimon.model.Player;
import com.progark.emojimon.model.Position;

public class PlayerController {
    private Player player;
    private GameBoard gameBoard;
    private Position startPosition, endPosition;

    public PlayerController(Player player, GameBoard gameBoard){
        this.player = player;
        this.gameBoard = gameBoard;
    }

    // roll dice on touch
    public void onTouchRollDice(){
        gameBoard.rollDice();
    }

    // selected piece to move
    public void onTouchChooseFirstPos(Position startPosition){
        // if position is owned by player, set startPosition
        if (startPosition.getOwner() == player){
            this.startPosition = startPosition;
        }
    }

    // select where to move piece to
   /* public void onTouchSelectEndPos(Position endPosition){
       // Move move = new Move(startPosition.getPositionIndex(), endPosition.getPositionIndex());
        if (gameBoard.movePiece(move)){
            player.updateDice();
            // TODO: save move for player (opt: save move player has made to animate for the opponent?)
            // if piece can be cleared up, update players pieces
            if (player.canClear(gameBoard.getBoardPositions())) {
                player.updatePieceClearance();
            }
        }
    }*/

}
