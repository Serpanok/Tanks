package serpanok.com.vk.tanks;

public class TanksObject  {
	//��������� �� �����
	public int x;
	public int y;
	
	public int createTime;
	
	public boolean isActive = true;
	
	//������ �� ������ ���� ����
	public TanksGame TanksGame;
	
	/* ��� ����������
	 * 
	 * 0 - �����
	 * 1 - �����
	 * 2 - ����
	 * 3 - �����
	 * 4 - ����
	 * 5 - ����� �����
	 * 6 - ����
	 * 7 - ����� */
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
