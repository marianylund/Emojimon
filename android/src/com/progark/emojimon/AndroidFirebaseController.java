package com.progark.emojimon;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progark.emojimon.controller.FirebaseControllerInterface;
import com.progark.emojimon.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AndroidFirebaseController implements FirebaseControllerInterface {
    // Needs to be initiated only once when sent to the core module by Android Launcher

    // Write a message to the db
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    private DatabaseReference myRef = db.getReference("message");
    private DatabaseReference Games = db.getReference("Games");

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private HashMap<String, GameData> gamesData = new HashMap<>();
    private HashMap<String, LastTurnData> lastTurnData = new HashMap<>(); // the same key as the games id


    public void Write() {
        myRef.setValue("testingtesting");
    }

    // TODO Where do emoji chosen by player are saved?
    public void addNewGame(Player creatorPlayer, List<List<Integer>> gameState){
        GameData gd = new GameData(creatorPlayer, gameState);

        String gameID = Games.push().getKey();
        Games.child(gameID).child("Player0").setValue(creatorPlayer);
        Games.child(gameID).child("GameState").setValue(gameState);

        gamesData.put(gameID, gd);

        addGameDataChangeListener(gameID); // Listen to changes of that game
    }

    public void addLastTurnByGameID(String gameID, Player player, String timeEnd, int[] dices, int[][] actions){
        LastTurnData ltd = new LastTurnData(player, timeEnd, dices, actions);

        String ltdID = Games.child(gameID).push().getKey();
        Games.child(gameID).child(ltdID).child("Player").setValue(player);
        Games.child(gameID).child(ltdID).child("TimeEnd").setValue(timeEnd);
        Games.child(gameID).child(ltdID).child("Dices").setValue(dices);
        Games.child(gameID).child(ltdID).child("Actions").setValue(actions);

        lastTurnData.put(gameID, ltd);
    }


    @Override
    public List<List<Integer>> getGameStateByGameID(String id) {
        return gamesData.get(id).getGameState();
    }

    private void addGameDataChangeListener(String gameID){
        Games.child(gameID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Update that gameData class

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
