package serpanok.com.vk.tanks.desktop;

import serpanok.com.vk.tanks.TanksKeyChecker;

import org.lwjgl.input.Keyboard;

public class DesktopKeyChecker extends TanksKeyChecker {

	public boolean isTop()
	{
		return Keyboard.isKeyDown( Keyboard.KEY_W );
	}
	
	public boolean isLeft()
	{
		return Keyboard.isKeyDown( Keyboard.KEY_A );
	}
	
	public boolean isRight()
	{
		return Keyboard.isKeyDown( Keyboard.KEY_D );
	}
	
	public boolean isBack()
	{
		return Keyboard.isKeyDown( Keyboard.KEY_S );
	}
	
	public boolean isSpace()
	{
		return Keyboard.isKeyDown( Keyboard.KEY_SPACE );
	}
	
	public boolean getKet( int key )
	{
		return Keyboard.isKeyDown( key );
	}
	
}
