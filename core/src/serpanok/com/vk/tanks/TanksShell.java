package serpanok.com.vk.tanks;

public class TanksShell extends TanksObject {

	public TanksTank tank;		//��� ��� �������� ������
	public int direction;		//����������� ��������
	
	public boolean move()
	{
		int newX = this.x;
		int newY = this.y;
		
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
			
		System.out.println("shell move to x=" + newX + "/y=" +  newY);
		
		//������� �� ��������� � �����
		if( TanksGame.map[newY][newX].componentType > 2 )
		{
			this.TanksGame.map[newY][newX].hit( this.tank );
			this.isActive = false;
		}
		//�������� �� ��������� � ����
		for(int i=0; i < this.TanksGame.tanks.size(); i++)
		{
			TanksTank tempTank = this.TanksGame.tanks.get(i);
			
			if(tempTank.isActive && tempTank.x == newX && tempTank.y == newY)
			{
				this.TanksGame.tanks.get(i).hit( this.tank );
				this.isActive = false;
			}
		}
		
		this.x = newX;
		this.y = newY;
		
		return true;
	}
	
	TanksShell( TanksTank tank_ )
	{
		
		System.out.println("shell direction move " +  tank_.direction);
		this.x			= tank_.x;
		this.y			= tank_.y;
		this.tank		= tank_;
		this.direction	= tank_.direction;
		this.TanksGame	= tank_.TanksGame;
		
		this.move();
	}
}
