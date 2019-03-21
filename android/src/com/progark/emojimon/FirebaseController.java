package com.progark.emojimon;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.progark.emojimon.controller.FirebaseControllerInterface;

public class FirebaseController implements FirebaseControllerInterface {
    // Needs to be initiated only once when sent to the core module by Android Launcher

    // Write a message to the db
    FirebaseDatabase db = FirebaseDatabase.getInstance();

    DatabaseReference myRef = db.getReference("message");
    DatabaseReference Games = db.getReference("Games");
    DatabaseReference Players = db.getReference("Players");

    FirebaseDatabase database = FirebaseDatabase.getInstance();


    public void Write() {
        myRef.setValue("testingtesting");
    }


    public void getGameStatusByID(int id) {
        final String GameID = "Game_" + id;
        Players.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(GameID)) {
                    String GameStatus = snapshot.child(GameID).child("Status").getValue().toString();
                    Log.d("Test", GameStatus);

                } else {
                    Log.e("Test", "Asked game does not exist", new IllegalArgumentException());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO handle erros
                Log.d("test", databaseError.getMessage());
            }
        });
    }

    public void SetEmojiByPlayerID(int id, final String link) {
        final String PlayerID = "Player_" + id;
        //TODO check if the link is valid ?

        Players.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (snapshot.hasChild(PlayerID)) {
                    Log.d("Test", snapshot.child(PlayerID).getValue().toString());
                    Players.child(PlayerID).child("Emoji").setValue(link);
                } else {
                    Log.e("Test", "Asked player does not exist", new IllegalArgumentException());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO handle erros
                Log.d("test", databaseError.getMessage());
            }
        });
    }

    @Override
    public void getPlayerByID(int id) {
        Log.d("test", "Got player by id");
    }
}
