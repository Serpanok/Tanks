package serpanok.com.vk.tanks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import serpanok.com.vk.tanks.TanksGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tanks";
        config.width = 660;
        config.height = 660;
        config.resizable = false;
		new LwjglApplication(new TanksGame(), config);
	}
}
