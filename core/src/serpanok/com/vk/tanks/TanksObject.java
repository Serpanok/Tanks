package serpanok.com.vk.tanks;

public class TanksObject  {
	//положение на карте
	public int x;
	public int y;
	
	public int createTime;
	
	public boolean isActive = true;
	
	//ссылка на объект ядра игры
	public TanksGame TanksGame;
	
	/* Тип компонента
	 * 
	 * 0 - земля
	 * 1 - трава
	 * 2 - вода
	 * 3 - стена
	 * 4 - база
	 * 5 - спавн ботов
	 * 6 - танк
	 * 7 - взрыв */
	public int componentType = 0;
	
	public boolean hit( TanksObject tank_ )
	{
		System.out.println("HIT!!!!!!!!");
		return true;
	}
	
	TanksObject( int x_, int y_, TanksGame TanksGame_ )
	{	
		this.x = x_;
		this.y = y_;
		this.TanksGame = TanksGame_;
		this.createTime = TanksGame_.frameI;
	}
	
	TanksObject()
	{
	}
}
