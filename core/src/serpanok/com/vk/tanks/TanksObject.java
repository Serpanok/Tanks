package serpanok.com.vk.tanks;

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/

public class TanksObject  {
	//положение на карте
	public int x;
	public int y;
	
	/* Тип компонента
	 * 
	 * 0 - земля
	 * 1 - трава
	 * 2 - вода
	 * 3 - стена
	 * 4 - база
	 * 5 - спавн ботов
	 * 6 - танк */
	public int componentType = 0;
	
	TanksObject()
	{	
	}
}
