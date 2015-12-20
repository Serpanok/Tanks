package serpanok.com.vk.tanks;

public class TanksSpawn extends TanksObject {
	
	boolean create( boolean forcibly )
	{
		if( this.TanksGame.botsLeft <= 0 )
		{
			return false;
		}
		
		System.out.println("Spawn.Create");
		if( this.TanksGame.randomizer.isEvent( this.TanksGame.SPAWN_COEFFICIENT ) || forcibly )
		{
			for( int i=0; i < this.TanksGame.tanks.size(); i++)
			{
				if( this.TanksGame.tanks.get(i).isActive && this.TanksGame.tanks.get(i).x == this.x && this.TanksGame.tanks.get(i).y == this.y )
				{
					return false;
				}
			}
			
			System.out.println("Spawn.Create.true");
			this.TanksGame.tanks.add( new TanksTank(0,
													0,
													true,
													this.TanksGame.randomizer.rand.nextInt(3)+1,
													this.x,
													this.y,
													this.TanksGame
													)
									);
			
			this.TanksGame.botsLeft--;
			
			return true;
		}
		return false;
	}
	
	TanksSpawn( int x_, int y_, TanksGame TanksGame_ )
	{
		this.x = x_;
		this.y = y_;
		
		this.TanksGame = TanksGame_;
	}
	
}
