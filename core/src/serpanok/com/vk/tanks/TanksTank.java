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
	
	public int lastMove = 0;
	public int lastShot = 0;
	
	public boolean move( int moveDirection )
	{
		//�������� �� ����������� ��������
		if( this.TanksGame.frameI < this.lastMove + this.TanksGame.TANK_SPEED )
		{
			return false;
		}
		lastMove = this.TanksGame.frameI;
		
		//���� ������� �����
		if( moveDirection != this.direction )
		{
			this.direction = moveDirection;
		}
		//���� ��������
		else
		{
			int newX = x;
			int newY = y;
			//�����
			if( this.direction == 0)
			{
				newY++;
			}
			//������
			else if( this.direction == 1)
			{
				newX++;
			}
			//����
			else if( this.direction == 2)
			{
				newY--;
			}
			//�����
			else if( this.direction == 3)
			{
				newX--;
			}
			
			System.out.println("tank move(" + this.direction + ") to x=" + newX + "/y=" +  newY);
			//�������� �� ������� �������� �� ����
			if( this.TanksGame.map[newY][newX].componentType > 1 )
			{
				return false;
			}
			//�������� �� ������� ����� �� ����
			for(int i=0; i < this.TanksGame.tanks.size(); i++)
			{
				TanksTank tempTank = this.TanksGame.tanks.get(i);
				
				if(tempTank.isActive && tempTank.x == newX && tempTank.y == newY)
				{
					return false;
				}
			}
			
			x = newX;
			y = newY;
		}
		return true;
	}
	
	
	public boolean shot()
	{
		//�������� �� ����������� ��������
		if( this.TanksGame.frameI < this.lastShot + this.TanksGame.SHOT_SPEED )
		{
			return false;
		}
		lastShot = this.TanksGame.frameI;
		
		System.out.println("SHOT!!!!!!!!");
		TanksGame.shells.add( new TanksShell( this ) );
		
		return true;
	}
	
	public boolean hit( TanksObject tank_ )
	{
		System.out.println("HIT TO TANK!!!!!!!!" + this.strength);
		
		this.strength--;
		
		if(this.strength <= 0)
		{
			System.out.println("TANK destroy!!!!!!!!" + this.strength);
			this.isActive = false;
			
			this.TanksGame.bangs.add( new TanksObject( this.x, this.y, this.TanksGame ) );
			
			if(!isBot)
			{
				//this.TanksGame.isGameActive = false;
			}
			return true;
		}
		
		return false;
	}
	
	TanksTank()
	{
		type		= 1;
		level		= 0;
		isBot		= true;
		direction	= 2;
	}
	
	TanksTank( int type_, int level_, boolean isBot_, int direction_, int x_, int y_, TanksGame TanksGame_)
	{
		System.out.println("tank create");
		
		this.type		= type_;
		this.level		= level_;
		this.isBot		= isBot_;
		this.direction	= direction_;
		this.TanksGame	= TanksGame_;
		
		this.strength	= 1;
		
		this.componentType = 6;
		this.x = x_;
		this.y = y_;
	}
}
