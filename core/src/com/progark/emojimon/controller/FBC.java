package com.progark.emojimon.controller;

public class FBC {

    // Comes from FirebaseController class in Android module
    private FirebaseControllerInterface firebase;

    // Using Singleton pattern in order to access it from any class
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
