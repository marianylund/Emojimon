package com.progark.emojimon;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseController {

    private static FirebaseController INSTANCE = null;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");
    public void Write(){
        myRef.setValue("testingtesting");
    }

    public static FirebaseController Instance(){
        if(INSTANCE == null){
            INSTANCE = new FirebaseController();
        }
        return INSTANCE;
    }

    private void FirebaseController(){

    }
}
