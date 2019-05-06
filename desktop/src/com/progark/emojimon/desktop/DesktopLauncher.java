package com.progark.emojimon.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.progark.emojimon.Emojimon;
//import com.progark.emojimon.controller.FirebaseControllerInterface;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = Emojimon.WIDTH;
		config.height = Emojimon.HEIGHT;
		config.title = "Emojimon";
		config.useGL30 = false;

		//new LwjglApplication(new Emojimon(), config);

		new LwjglApplication(new Emojimon(new DesktopFirebaseController()), config);
	}
}
