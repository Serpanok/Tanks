package serpanok.com.vk.tanks;

public class TanksBonus extends TanksObject {
	
	public int type;				//тип
	public TanksObject tank;		//тот кто использовал бонус
	public int deadline;			//время действия
	
	public int useStatus = 0;		//статус использования (0 - не подобран; 1 - подобран и активен; 2 - подобран и не активен)
	
	TanksBonus( int type_, TanksGame TanksGame_ )
	{
		this.type = type_;
		this.TanksGame = TanksGame_;
		this.deadline = this.TanksGame.frameI + this.TanksGame.BONUS_LIVE;
		
		this.x = this.TanksGame.randomizer.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
		this.y = this.TanksGame.randomizer.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
	}
	
	TanksBonus( TanksGame TanksGame_ )
	{
		this.TanksGame = TanksGame_;
		this.type = this.TanksGame.randomizer.rand.nextInt( 2 );
		this.deadline = this.TanksGame.frameI + this.TanksGame.BONUS_LIVE;
		
		this.x = this.TanksGame.randomizer.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
		this.y = this.TanksGame.randomizer.rand.nextInt( this.TanksGame.AREA_SIZE - 2 ) + 1;
	}
}
