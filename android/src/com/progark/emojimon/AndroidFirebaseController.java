package com.progark.emojimon;

import android.nfc.Tag;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progark.emojimon.controller.FirebaseControllerInterface;
import com.progark.emojimon.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class AndroidFirebaseController implements FirebaseControllerInterface {
    // Needs to be initiated only once when sent to the core module by Android Launcher

    // Write a message to the db
    private final String TAG = "sondre";
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    private DatabaseReference myRef = db.getReference("message");
    private DatabaseReference Games = db.getReference("Games");

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private GameData gd;

    private HashMap<String, GameData> gamesData = new HashMap<>();
    private HashMap<String, LastTurnData> lastTurnData = new HashMap<>(); // the same key as the games id

    String gameId = null;


    public void Write() {
        myRef.setValue("testingtesting");
    }

    // TODO Where do emoji chosen by player are saved?
    public void addNewGame(String creatorPlayer){
        GameData gd = new GameData(creatorPlayer);

        String gameID = Games.push().getKey();
        Games.child(gameID).setValue(gd);
        gamesData.put(gameID, gd);

        addGameDataChangeListener(gameID); // Listen to changes of that game
    }

    public void addLastTurnByGameID(String gameID, Player player, String timeEnd, int[] dices, int[][] actions){
        LastTurnData ltd = new LastTurnData(player, timeEnd, dices, actions);

        String ltdID = Games.child(gameID).push().getKey();

        lastTurnData.put(gameID, ltd);
    }


    /*@Override
    public List<List<Integer>> getGameStateByGameID(String id) {
        return gamesData.get(id).getGameState();
    }*/

    public void addGameDataChangeListener(final String gameID){
        Games.child(gameID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GameData sm = dataSnapshot.getValue(GameData.class);
                gamesData.put(gameID, sm); //Update that gameData class
                // TODO Notify subscribers that the data has been changed?

                /* Debugging
                if(sm == null){
                    Log.d("sondre", "GD is null");
                } else {
                    Log.d("sondre", sm.toString());
                    Log.d("sondre", "Player0" + sm.getPlayer0Key());
                    Log.d("sondre", "GameState" + sm.getGameState());
                }*/

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    // Finds the first game with status == waiting.
    // Sets player 1 to true and subscribes to the gamedata
    public void joinGame() {
        Games.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()){
                    if(snap.child("Status").getValue().equals("waiting")) {
                        addPlayerToGame(snap.getKey());
                        addGameDataChangeListener(snap.getKey());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });
    }

    void addPlayerToGame(String gameId) {
        Games.child(gameId + "/player1").setValue(true);
    }
}
