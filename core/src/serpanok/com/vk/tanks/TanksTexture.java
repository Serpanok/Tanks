package serpanok.com.vk.tanks;

public class TanksTexture extends TanksObject {
	
	/* 0 - ������,
	 * 1 - �����
	 * 2 - ����
	 * 3 - ��
	 * 4 - ����(1)
	 * 5 - ����(2) */
	public int type;			//���
	public int strength	= -1;	//���������� ���������
	
	public boolean hit( TanksObject tank_ )
	{
		System.out.println("HIT TEXTURE!!!!!!!!" + x + "/" + y);
		
		//���� ��� ������ - ��������� ���������
		if( type == 0 )
		{
			strength--;
			
			//���� ��������� ����� �� 0 �� ���������� ������ � �����
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
