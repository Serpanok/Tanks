package serpanok.com.vk.tanks;

public class TanksBase extends TanksObject {
	
	public int strength;	//���������� ���������
	
	public boolean hit( TanksObject tank_ )
	{
		System.out.println("HIT BASE!!!!!!!!" + x + "/" + y);
		
		strength--;
			
		//���� ��������� ����� �� 0 �� ���������� ������ � �����
		if(strength <= 0)
		{
			this.TanksGame.isGameActive = false;
			return true;
		}
		
		return false;
	}
	
	TanksBase( TanksGame TanksGame_ )
	{
		this.TanksGame = TanksGame_;
	}
	
}
