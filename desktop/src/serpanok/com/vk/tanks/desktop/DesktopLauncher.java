package serpanok.com.vk.tanks.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import serpanok.com.vk.tanks.TanksGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Tanks";
        config.resizable = false;
        
        TanksGame TanksGame = new TanksGame( new DesktopKeyChecker() );
        
        config.width = TanksGame.AREA_SIZE * TanksGame.BLOCK_SIZE * 2;
        config.height = TanksGame.AREA_SIZE * TanksGame.BLOCK_SIZE * 2;
        
        new LwjglApplication(TanksGame, config);
	}
}
