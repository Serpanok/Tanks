package serpanok.com.vk.tanks;

/*import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;*/

public class TanksTank extends TanksObject {
	
	public int type;				//���
	public int level;
	public boolean isBot;			//���?
	public TanksObject[] bonuses;	//������
	public int direction;			//����������� ��������
	public int team;				//�������
	public int strength;			//���������� ���������
	
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
