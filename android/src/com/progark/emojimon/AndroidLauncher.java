package com.progark.emojimon;

import android.os.Bundle;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new Emojimon(new AndroidFirebaseController()), config);

		//AndroidFirebaseController fb = new AndroidFirebaseController();
		//fb.Write();

		Log.d("Test", "Hello");
		//fb.SetEmojiByPlayerID(0, "New l");
	}
}
