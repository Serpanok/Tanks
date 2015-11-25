package serpanok.com.vk.tanks;

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/

public class TanksTank extends TanksObject {
	
	public int type;				//тип
	public boolean isBot;			//бот?
	public TanksObject[] bonuses;	//бонусы
	public int direction;			//направление движения
	public int team;				//команда
	public int strength;			//остаточная прочность
	
}
