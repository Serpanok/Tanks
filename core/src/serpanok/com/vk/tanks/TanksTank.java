package serpanok.com.vk.tanks;

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/

public class TanksTank extends TanksObject {
	
	public int type;				//тип
	public int level;
	public boolean isBot;			//бот?
	public TanksObject[] bonuses;	//бонусы
	public int direction;			//направление движения
	public int team;				//команда
	public int strength;			//остаточная прочность
	
	TanksTank()
	{
		type		= 1;
		level		= 0;
		isBot		= true;
		direction	= 2;
	}
	
	TanksTank( int type_, int level_, boolean isBot_, int direction_, int x_, int y_)
	{
		System.out.println("tank create");
		
		type		= type_;
		level		= level_;
		isBot		= isBot_;
		direction	= direction_;
		
		componentType = 6;
		x = x_;
		y = y_;
	}
}
