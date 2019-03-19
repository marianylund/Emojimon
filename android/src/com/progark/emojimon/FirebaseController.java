package com.progark.emojimon;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseController {

    // Write a message to the db
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference myRef = db.getReference("message");
    DatabaseReference Players = db.getReference("Players");
    DatabaseReference Games = db.getReference("Games");

    public void Write(){
        myRef.setValue("testingtesting");
    }

    public void getGameStatusByID(int id){
        final String GameID = "Game_" + id;
        Players.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.hasChild(GameID)){
                    String GameStatus = snapshot.child(GameID).child("Status").getValue().toString();
                    Log.d("Test", GameStatus);

                } else {
                    Log.e("Test", "Asked game does not exist", new IllegalArgumentException());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO handle erros
                Log.d("test",databaseError.getMessage());
            }
        });
    }

    public void SetEmojiByPlayerID(int id, final String link){
        final String PlayerID = "Player_" + id;
        //TODO check if the link is valid ?

        Players.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if(snapshot.hasChild(PlayerID)){
                    Log.d("Test", snapshot.child(PlayerID).getValue().toString());
                    Players.child(PlayerID).child("Emoji").setValue(link);
                } else {
                    Log.e("Test", "Asked player does not exist", new IllegalArgumentException());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //TODO handle erros
                Log.d("test",databaseError.getMessage());
            }
        });


    }
}
