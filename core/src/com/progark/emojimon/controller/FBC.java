package com.progark.emojimon.controller;

import com.progark.emojimon.model.interfaces.FirebaseControllerInterface;

public class FBC {

    private FirebaseControllerInterface firebase;

    // Using Singleton pattern in order to have only one instance of FirebaseController
    private static FBC INSTANCE = null;

    public static FBC I() {
        if (INSTANCE == null) {
            INSTANCE = new FBC();
        }
        return INSTANCE;
    }

    private void FBC () {

    }

    public void setFirebase(FirebaseControllerInterface firebase){
        this.firebase = firebase;
    }

    public FirebaseControllerInterface get(){
        return firebase;
    }
}
