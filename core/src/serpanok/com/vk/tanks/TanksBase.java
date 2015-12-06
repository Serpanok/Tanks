package serpanok.com.vk.tanks;

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/

public class TanksBase extends TanksObject {
	
	public int strength;	//остаточная прочность
	
	public boolean hit( TanksObject tank_ )
	{
		System.out.println("HIT BASE!!!!!!!!" + x + "/" + y);
		
		strength--;
			
		//если прочность упала до 0 то превращаем объект в землю
		if(strength <= 0)
		{
			System.out.println("HIT BASE!!!!!!!!" + x + "/" + y);
			return true;
		}
		
		return false;
	}
	
}
