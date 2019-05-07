package com.progark.emojimon;

import android.support.annotation.NonNull;
import android.util.Log;

import com.badlogic.gdx.Game;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progark.emojimon.model.fireBaseData.Settings;
import com.progark.emojimon.model.interfaces.FirebaseControllerInterface;
import com.progark.emojimon.model.fireBaseData.GameData;
import com.progark.emojimon.model.fireBaseData.LastTurnData;
import com.progark.emojimon.model.interfaces.SubscriberToFirebase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class AndroidFirebaseController implements FirebaseControllerInterface {
    // Needs to be initiated only once when sent to the core module by Android Launcher

    // Write a message to the db
    private final String TAG = "sondre";
    private FirebaseDatabase db = FirebaseDatabase.getInstance();

    private DatabaseReference myRef = db.getReference("message");
    private DatabaseReference Games = db.getReference("Games");
    private DatabaseReference LastTurns = db.getReference("LastTurns");

    private FirebaseDatabase database = FirebaseDatabase.getInstance();

    private GameData gd;

    private HashMap<String, GameData> gamesData = new HashMap<>();
    private HashMap<String, LastTurnData> lastTurnData = new HashMap<>(); // the same key as the games id
    private List<SubscriberToFirebase> subscribers = new ArrayList<>();

    String gameId = null;


    public void testWrite(String testMessage) {
        myRef.setValue(testMessage);
    }

    //region NEW GAME
    public void addNewGame(String creatorPlayer, Settings strategies){
        GameData gd = new GameData(creatorPlayer, strategies);

        String gameID = Games.push().getKey();
        Games.child(gameID).setValue(gd);
        gamesData.put(gameID, gd);

        GameManager.GetInstance().setLocalPlayer(false); // Player 0 if created the game
        GameManager.GetInstance().setGameID(gameID);
        addSubscriber(GameManager.GetInstance());

        addGameDataChangeListener(gameID); // Listen to changes of that game
    }

    public void addGameDataChangeListener(final String gameID){
        Games.child(gameID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GameData sm = dataSnapshot.getValue(GameData.class);
                gamesData.put(gameID, sm); //Update that gameData class
                System.out.println(gamesData);
                notifyGameDataSubs(gameID, sm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //endregion

    //region NEW LAST TURN
    public void addLastTurnByGameID(String gameID, boolean player, List<Integer> dices, List<List<Integer>> moves){
        //TODO Better error handling on gameID

        if(!gamesData.containsKey("gameID")){
            Log.d("sondre", "There is no game with this GameID, jsyk");
        }

        LastTurnData ltd = new LastTurnData(player, dices, moves);
        LastTurns.child(gameID).setValue(ltd);
        lastTurnData.put(gameID, ltd);

        addLastTurnDataChangeListener(gameID);
    }

    public void addLastTurnDataChangeListener(final String gameID){
        LastTurns.child(gameID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LastTurnData sm = dataSnapshot.getValue(LastTurnData.class);
                lastTurnData.put(gameID, sm); //Update that LastTurnData class

                notifyLastTurnSubs(gameID,sm);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //endregion

    //region SETTERS

    public void setGameStatusByGameID(String gameID, String newStatus){
        //TODO Check if it is an allowed status to set
        if(gamesData.containsKey(gameID)){
            Games.child(gameID).child("status").setValue(newStatus);
        }else{
            throw new IllegalArgumentException("There is no game with the " + gameID + " ID");
        }
    }

    public void setGameBoardByGameID(String gameID, List<List<Integer>> gameBoard){
        //TODO Check if it is an allowed gameBoard format
        if(gamesData.containsKey(gameID)){
            Games.child(gameID).child("gameBoard").setValue(gameBoard);
        }else{
            throw new IllegalArgumentException("There is no game with the " + gameID + " ID");
        }
    }

    //end region

    // Finds the first game with status == waiting.
    // Sets player 1 to true and subscribes to the gamedata
    public void joinGame() {
        Games.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snap : dataSnapshot.getChildren()){
                    if(snap.child("status").getValue().equals("Waiting")) {
                        addPlayerToGame(snap.getKey());
                        addGameDataChangeListener(snap.getKey());
                        //addLastTurnDataChangeListener(snap.getKey());
                        break;
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {}
        });

    }

    void addPlayerToGame(String gameID) {
        Games.child(gameID + "/player1").setValue(true);
        GameManager.GetInstance().setGameID(gameID);
        GameManager.GetInstance().setLocalPlayer(true); // Player 1 if joined game
        addSubscriber(GameManager.GetInstance());
    }



    @Override
    public void addSubscriber(SubscriberToFirebase subscriber) {
        if(!subscribers.contains(subscriber)){
            subscribers.add(subscriber);
        }
    }

    @Override
    public ArrayList getGameStateByGameID(String id) {
        return null;
    }

    private void notifyLastTurnSubs(String gameID, LastTurnData ld){
        for (SubscriberToFirebase sub:subscribers) {
            sub.notifyOfNewLastTurn(gameID, ld);
        }
    }

    private void notifyGameDataSubs(String gameID, GameData gd){
        for (SubscriberToFirebase sub:subscribers) {
            sub.notifyOfGameData(gameID, gd);
        }
    }

    public Object[] getGameIDs(){
        return gamesData.keySet().toArray();
    }
}
