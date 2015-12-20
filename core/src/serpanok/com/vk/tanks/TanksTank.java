package serpanok.com.vk.tanks;

public class TanksTank extends TanksObject {
	
	public int type;				//тип
	public int level;
	public int life;				//остаточные жизни
	public boolean isBot;			//бот?
	public TanksBonus bonus;		//бонус
	public int direction;			//направление движения
	public int team;				//команда
	public int strength;			//остаточная прочность
	
	public int lastMove = 0;
	public int lastShot = 0;
	
	public boolean isCanMove()
	{
		if( this.TanksGame.frameI < this.lastMove + this.TanksGame.TANK_SPEED[this.level] )
		{
			return false;
		}
		return true;
	}
	
	public boolean move( int moveDirection )
	{
		//проверка на возможность движения
		if( !this.isCanMove() )
		{
			return false;
		}
		lastMove = this.TanksGame.frameI;
		
		//если поворот танка
		if( moveDirection != this.direction )
		{
			this.direction = moveDirection;
		}
		//если движение
		else
		{
			int newX = x;
			int newY = y;
			//вверх
			if( this.direction == 0)
			{
				newY++;
			}
			//вправо
			else if( this.direction == 1)
			{
				newX++;
			}
			//вниз
			else if( this.direction == 2)
			{
				newY--;
			}
			//влево
			else if( this.direction == 3)
			{
				newX--;
			}
			
			System.out.println("tank move(" + this.direction + ") to x=" + newX + "/y=" +  newY);
			//проверка на наличие текстуры на пути
			if( this.TanksGame.map[newY][newX].componentType > 1 )
			{
				return false;
			}
			//проверка на наличие танка на пути
			for(int i=0; i < this.TanksGame.tanks.size(); i++)
			{
				TanksTank tempTank = this.TanksGame.tanks.get(i);
				
				if(tempTank.isActive && tempTank.x == newX && tempTank.y == newY)
				{
					return false;
				}
			}
			
			//проверка на наличие бонуса на пути
			for(int i=0; i < this.TanksGame.bonuses.size(); i++)
			{
				TanksBonus tempBonus = this.TanksGame.bonuses.get(i);
				
				if(tempBonus.isActive && tempBonus.x == newX && tempBonus.y == newY)
				{
					tempBonus.tank = this;
					
					//если бонус длительный...
					if(tempBonus.type == 0)
					{
						//если есть возможность повышения 
						if(this.level < 3)
						{
							this.level++;
						}
						//если уже максимальный уровень - добавляем жизнь
						else
						{
							this.life++;
						}
					}
					else if(tempBonus.type == 1)
					{
						System.out.println("BEFORE DESTROY: " + this.TanksGame.tanks.size());
						for(int j=1; j < this.TanksGame.tanks.size(); j++)
						{
							System.out.println("CHECK FOR DESTROY: " + j);
							if( this.TanksGame.tanks.get(j).isActive && this.TanksGame.tanks.get(j).isBot )
							{
								System.out.println("DESTROY: " + j);
								this.TanksGame.tanks.get(j).hit(this);
							}
						}
						System.out.println("AFTER DESTROY: " + this.TanksGame.tanks.size());
					}
				}
			}
			
			x = newX;
			y = newY;
		}
		return true;
	}
	
	
	public boolean shot()
	{
		//проверка на возможность движения
		if( this.TanksGame.frameI < this.lastShot + this.TanksGame.SHOT_SPEED[this.level] )
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
			
			if(!this.isBot)
			{
				this.TanksGame.isGameActive = false;
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
