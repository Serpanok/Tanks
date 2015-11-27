package serpanok.com.vk.tanks;

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/

public class TanksTexture extends TanksObject {
	
	/* 0 - кирпич,
	 * 1 - сталь
	 * 2 - куст
	 * 3 - лёд
	 * 4 - вода(1)
	 * 5 - вода(2) */
	public int type;			//тип
	public int strength	= -1;	//остаточная прочность
	
	TanksTexture( int type_ )
	{
		this.type = type_;
		
		if(type_ == 0)
		{
			this.strength = 3;
		}
	}
}
