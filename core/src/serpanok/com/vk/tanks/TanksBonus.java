package serpanok.com.vk.tanks;

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/

public class TanksBonus extends TanksObject {
	
	public int type;				//тип
	public TanksObject tank;		//тот кто использовал бонус
	public int deadline;			//время действия
	
	TanksBonus( int type_, TanksGame TanksGame_ )
	{
		this.type = type_;
		this.TanksGame = TanksGame_;
		this.deadline = this.TanksGame.frameI + this.TanksGame.BONUS_LIVE;
		
		this.x = this.TanksGame.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
		this.y = this.TanksGame.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
	}
	
	TanksBonus( TanksGame TanksGame_ )
	{
		this.TanksGame = TanksGame_;
		this.type = this.TanksGame.rand.nextInt( 6 );
		this.deadline = this.TanksGame.frameI + this.TanksGame.BONUS_LIVE;
		
		this.x = this.TanksGame.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
		this.y = this.TanksGame.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
	}
}
