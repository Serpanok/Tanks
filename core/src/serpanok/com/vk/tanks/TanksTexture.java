package serpanok.com.vk.tanks;

public class TanksTexture extends TanksObject {
	
	/* 0 - кирпич,
	 * 1 - сталь
	 * 2 - куст
	 * 3 - лёд
	 * 4 - вода(1)
	 * 5 - вода(2) */
	public int type;			//тип
	public int strength	= -1;	//остаточная прочность
	
	public boolean hit( TanksObject tank_ )
	{
		System.out.println("HIT TEXTURE!!!!!!!!" + x + "/" + y);
		
		//если это кирпич - уменьшаем прочность
		if( type == 0 )
		{
			strength--;
			
			//если прочность упала до 0 то превращаем объект в землю
			if(strength <= 0)
			{
				TanksGame.map[y][x].componentType = 0;
				return true;
			}
		}
		
		return false;
	}
	
	TanksTexture( int type_, TanksGame TanksGame_ )
	{
		this.type = type_;
		this.TanksGame = TanksGame_;
		
		if(type_ == 0)
		{
			this.strength = 2;
		}
	}
}
